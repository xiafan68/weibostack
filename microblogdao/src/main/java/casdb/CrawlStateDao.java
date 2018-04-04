package casdb;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

import casdb.DBUtil.PrepareStatementBounder;
import casdb.DBUtil.StatusFieldInSpector;
import kafkastore.model.RepostCrawlState;
import kafkastore.model.UserCrawlState;

public class CrawlStateDao {

	CassandraConn conn;
	PreparedStatement rpcrawlInsertStmt;
	PreparedStatement ucrawlInsertStmt;

	public CrawlStateDao(CassandraConn conn) {
		this.conn = conn;
		rpcrawlInsertStmt = conn
				.prepare(DBUtil.genPrepareStatement(RepostCrawlState.class, new StatusFieldInSpector()));
		ucrawlInsertStmt = conn.prepare(DBUtil.genPrepareStatement(UserCrawlState.class, new StatusFieldInSpector()));
	}

	public void putRepostState(RepostCrawlState state) {
		BoundStatement boundStatement = new BoundStatement(rpcrawlInsertStmt);
		PrepareStatementBounder bounder = new PrepareStatementBounder(boundStatement);
		DBUtil.genBound(bounder, state);
		conn.execute(boundStatement);
	}

	public RepostCrawlState getRepostStateByMid(String mid) {
		RepostCrawlState ret = null;
		ResultSet set = conn.query(String.format("select * from %s where mid = '%s'", DBUtil.REPOST_CRAWL_STATE, mid));
		for (Row row : set.all()) {
			ret = getRpCrawlState(row);
			break;
		}
		return ret;
	}

	private RepostCrawlState getRpCrawlState(Row row) {
		RepostCrawlState ret = new RepostCrawlState();
		ret.setMid(row.getString("mid"));
		ret.setLastCrawlNum(row.getShort("lastCrawlNum"));
		ret.setSinceID(row.getLong("sinceID"));
		ret.setLastCrawlTime(row.getLong("lastCrawlTime"));
		return ret;
	}

	public Iterator<RepostCrawlState> getAllRpCrawlState() {
		final ResultSet set = conn.query(String.format("select * from %s", DBUtil.REPOST_CRAWL_STATE));
		return new Iterator<RepostCrawlState>() {
			Iterator<Row> iter = set.iterator();

			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}

			@Override
			public RepostCrawlState next() {
				return getRpCrawlState(iter.next());
			}

			@Override
			public void remove() {

			}
		};
	}

	public void putUserCrawlState(UserCrawlState state) {
		BoundStatement boundStatement = new BoundStatement(ucrawlInsertStmt);
		PrepareStatementBounder bounder = new PrepareStatementBounder(boundStatement);
		DBUtil.genBound(bounder, state);
		conn.execute(boundStatement);
	}

	public UserCrawlState getUserCrawlStateByUid(String uid) {
		UserCrawlState ret = null;
		ResultSet set = conn.query(String.format("select * from %s where uid = '%s'", DBUtil.USER_CRAWL_STATE, uid));
		for (Row row : set.all()) {
			ret = getUserCrawlState(row);
			break;
		}
		return ret;
	}

	private UserCrawlState getUserCrawlState(Row row) {
		UserCrawlState ret = new UserCrawlState();
		ret.setUid(row.getString("uid"));
		ret.setLastCrawlNum(row.getShort("lastCrawlNum"));
		ret.setSinceID(row.getLong("sinceID"));
		ret.setLastCrawlTime(row.getLong("lastCrawlTime"));
		return ret;
	}

	public Iterator<UserCrawlState> getAllUserCrawlState() {
		final ResultSet set = conn.query(String.format("select * from %s", DBUtil.USER_CRAWL_STATE));
		return new Iterator<UserCrawlState>() {
			Iterator<Row> iter = set.iterator();

			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}

			@Override
			public UserCrawlState next() {
				return getUserCrawlState(iter.next());
			}

			@Override
			public void remove() {

			}
		};
	}

	public void updateTopicCrawlState(String topic, long time, int freq) {
		conn.executeCQL(String.format("update %s set freq = freq + %d where crawltype = '%s' and tstime = %d",
				DBUtil.CRAWL_STATS, freq, topic, time));
	}

	public Map<Long, Integer> getTopicCrawlState(String topic, long startTime, long endTime) {
		Map<Long, Integer> ret = new HashMap<Long, Integer>();
		for (Row row : conn.query(String.format(
				"select tstime, freq from %s where crawltype = '%s' and tstime >= %d and tstime < %d order by tstime",
				DBUtil.CRAWL_STATS, topic, startTime, endTime)).all()) {
			ret.put(row.getLong(0), (int) row.getLong(1));
		}
		return ret;
	}

	public void close() {

	}
}
