package casdb;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

import casdb.DBUtil.StatusFieldInSpector;
import casdb.DBUtil.StatusFieldValueInSpector;
import kafkastore.TimeSeriesUpdateState;
import util.DateUtil;
import weibo4j.model.Status;

public class TweetDao {
	PreparedStatement statusStatement;
	PreparedStatement rtInsertStmt;
	PreparedStatement rtQueryStmt;
	CassandraConn conn;

	public TweetDao(CassandraConn conn) {
		this.conn = conn;
		statusStatement = conn.prepare(DBUtil.genPrepareStatement(Status.class, new StatusFieldInSpector()));
		rtInsertStmt = conn.prepare("insert into rttable(omid, hashcode, content, rtmid) values(?,?,?,?)");
		rtQueryStmt = conn.prepare("select rtmid from rttable where omid = ? and  hashcode = ? and  content = ?;");
	}

	public void putTweet(Status status) {
		BoundStatement boundStatement = new BoundStatement(statusStatement);
		StatusFieldValueInSpector inspector = new StatusFieldValueInSpector(boundStatement);
		DBUtil.genBound(inspector, status);
		conn.execute(inspector.getBound());
	}

	public String getStatusContentByMid(String mid) {
		String ret = null;
		ResultSet set = conn.query(String.format("select * from %s where mid = '%s'", DBUtil.MICROBLOG_TABLE, mid));
		List<Row> rows = set.all();
		if (rows.size() > 0) {
			Row row = rows.get(0);
			// ret = DBUtil.toJSON(row);
			ret = row.getString("text");
		}
		return ret;
	}

	public String getStatusByMid(String mid) {
		String ret = null;
		ResultSet set = conn.query(String.format("select * from %s where mid = '%s'", DBUtil.MICROBLOG_TABLE, mid));
		List<Row> rows = set.all();
		if (rows.size() > 0) {
			Row row = rows.get(0);
			ret = DBUtil.toJSON(row);
		}
		return ret;
	}

	public boolean isRetweetProcessed(Status status) {
		boolean ret = false;
		String content = constructRetweetContent(status);
		String cql = String.format("select ");
		return ret;
	}

	private String constructRetweetContent(Status status) {
		return String.format("//@%s:%s", status.getUser().getName(), status.getText());
	}

	/**
	 * 更新转发表,这个表主要是用于确定那些微博是当前微博的父节点,主要是用于最大限度的还原转发链
	 * 
	 * @param status
	 */
	public void putRtweet(Status status) {
		if (status.getRetweetedStatus() != null && status.getUser() != null) {
			String content = "//@" + status.getUser().getName() + ":" + status.getText();
			BoundStatement stmt = new BoundStatement(rtInsertStmt);
			stmt.setString(0, status.getRetweetedStatus().getMid());
			stmt.setInt(1, content.hashCode());
			stmt.setString(2, content);
			stmt.setString(3, status.getMid());
			conn.execute(stmt);
		}
	}

	public List<String> getRtMids(String mid) {
		ResultSet set = conn.query("select rtmid from " + DBUtil.RETWEET_TABLE + "where omid='" + mid + "';");
		List<Row> rows = set.all();
		List<String> ret = new ArrayList<String>();
		for (Row row : rows) {
			ret.add(row.getString(0));
		}
		return ret;
	}

	/**
	 * 在时间hour的转发数+1
	 * 
	 * @param mid
	 * @param count
	 */
	public void update(String mid, long hour, int count) {
		conn.executeCQL(String.format("update %s set freq = freq + %s where mid='%s' and tstime = %s",
				DBUtil.RETWEET_SERIES_TABLE, count, mid, hour));
	}

	/**
	 * 查询转发序列
	 * 
	 * @param mid
	 * @param startTime
	 *            inclusive
	 * @param endTime
	 *            exclusive
	 * @return
	 */
	public List<long[]> queryTimeSeries(String mid, long startTime, long endTime) {
		List<long[]> ret = new ArrayList<long[]>();
		for (Row row : conn.query(String.format(
				"select tstime, freq from rtseries where mid = '%s' and tstime >= %d and tstime < %d order by tstime",
				mid, startTime, endTime)).all()) {
			ret.add(new long[] { row.getLong(0), row.getLong(1) });
		}
		return ret;
	}

	/**
	 * 更新所有父节点的转发次数
	 * 
	 * @param status
	 */
	public List<TimeSeriesUpdateState> updateRtTimeSeries(Status status) {
		assert status.getRetweetedStatus() != null;
		List<TimeSeriesUpdateState> updatedMids = new ArrayList<TimeSeriesUpdateState>();
		String omid = status.getRetweetedStatus().getMid();
		long updateTime = DateUtil.roundByHour(status.getCreatedAt().getTime());
		for (String rtContent : retweetSpliter(status)) {
			// ResultSet ret = conn
			// .query(String.format("select rtmid from %s where omid = '%s' and
			// hashcode=%d and content='%s';",
			// DBUtil.RETWEET_TABLE, omid, rtContent.hashCode(), rtContent));
			BoundStatement stmt = new BoundStatement(rtQueryStmt);
			stmt.setString(0, omid);
			stmt.setInt(1, rtContent.hashCode());
			stmt.setString(2, rtContent);
			ResultSet ret = conn.query(stmt);
			List<Row> rows = ret.all();
			if (rows.size() != 0) {
				String curMid = rows.get(0).getString(0);
				updatedMids.add(new TimeSeriesUpdateState(curMid, updateTime, false));
				update(curMid, updateTime, 1);
			}
		}

		updatedMids.add(new TimeSeriesUpdateState(omid, updateTime, false));
		update(omid, updateTime, 1);
		return updatedMids;
	}

	public static Pattern RT_PATTERN = Pattern.compile("(//[ ]*@([^:：]+)[:：])");

	public List<String> retweetSpliter(Status status) {
		List<String> ret = new ArrayList<String>();
		Matcher matcher = RT_PATTERN.matcher(status.getText());
		String content = status.getText();
		content = content.replaceAll("：", ":");
		content = content.replaceAll(" ", "");
		if (matcher.find()) {
			do {
				String curContent = status.getText().substring(matcher.start());
				ret.add(curContent);
			} while (matcher.find());
		}
		return ret;
	}
}
