package casdb;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.Factory;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BatchStatement.Type;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;

import collection.DefaultedPutMap;

/**
 * 为每条原始微博统计它的转发微博的相关统计信息，例如，人群信息（性别，用户类型,客户端），对于微博，
 * 
 * 
 * @author xiafan
 *
 */
public class RetweetStatsDao {
	public static final String GENDER_DIST = "rt_gender";
	private static final Object VIP_DIST = "rt_vip";
	private static final Object CLIENT_DIST = "rt_client";
	private static final Object LOCATION_DIST = "rt_location";
	private static final Object MOOD_DIST = "rt_mood";
	CassandraConn conn;
	ThreadLocal<BatchStatement> bStmt = new ThreadLocal<BatchStatement>();
	PreparedStatement clientStmt;

	public RetweetStatsDao(CassandraConn conn) {
		this.conn = conn;
		clientStmt = conn.prepare(
				String.format("update %s set freq = freq + ? where mid=? and tstime = ? and client=?", CLIENT_DIST));
	}

	/**
	 * 初始化retweet统计相关的量
	 * 
	 * @param conn
	 */
	public static void init(CassandraConn conn) {
		conn.executeCQL(String.format(
				"create table IF NOT EXISTS %s (mid text, tstime bigint, ismale text, freq counter, primary key(mid, tstime, ismale))",
				GENDER_DIST));
		conn.executeCQL(String.format(
				"create table IF NOT EXISTS  %s (mid text, tstime bigint, isvip boolean, freq counter, primary key(mid, tstime,isvip))",
				VIP_DIST));
		conn.executeCQL(String.format(
				"create table IF NOT EXISTS  %s (mid text, tstime bigint, client text, freq counter, primary key(mid, tstime,client))",
				CLIENT_DIST));

		conn.executeCQL(String.format(
				"create table IF NOT EXISTS  %s (mid text, tstime bigint, location text, freq counter, primary key(mid, tstime,location))",
				LOCATION_DIST));
		conn.executeCQL(String.format(
				"create table IF NOT EXISTS  %s (mid text, tstime bigint, mood text, freq counter, primary key(mid, tstime,mood))",
				MOOD_DIST));
	}

	public void beginBatch() {
		if (bStmt.get() != null) {
			conn.executeStmt(bStmt.get());
		}
		bStmt.set(new BatchStatement(Type.COUNTER));
	}

	public void endBatch() {
		conn.executeStmt(bStmt.get());
		bStmt.set(null);
	}

	/**
	 * 为每条原始微博mid，统计它的转发用户的性别在各个时间点的频次
	 * 
	 * @param mid
	 * @param timestamp
	 * @param freq
	 */
	public void putGenderForTweet(String mid, long tstime, String isMale, int freq) {
		String cql = String.format("update %s set freq = freq + %d where mid='%s' and tstime = %d and ismale='%s'",
				GENDER_DIST, freq, mid, tstime, isMale);
		execute(cql);
	}

	private void execute(String cql) {
		if (bStmt.get() == null) {
			conn.executeCQL(cql);
		} else {
			bStmt.get().add(new SimpleStatement(cql));
			if (bStmt.get().size() > 2) {
				endBatch();
				beginBatch();
			}
		}
	}

	private void executeStmt(Statement stmt) {
		if (bStmt.get() == null) {
			conn.executeStmt(stmt);
		} else {
			bStmt.get().add(stmt);
			if (bStmt.get().size() > 200) {
				endBatch();
				beginBatch();
			}
		}
	}

	public Map<String, Integer> getGenderForTweet(String mid, long start, long end) {
		Map<String, Integer> counter = DefaultedPutMap.decorate(new HashMap<String, Integer>(), new Factory() {
			@Override
			public Object create() {
				return 0;
			}
		});
		ResultSet set = conn
				.query(String.format("select ismale, freq from %s where mid = '%s' and tstime>= %d and tstime <= %d",
						GENDER_DIST, mid, start, end));
		for (Row row : set.all()) {
			counter.put(row.getString(0), (int) row.getLong(1));
		}
		return counter;
	}

	/**
	 * 为每条原始微博mid，统计它的转发用户的vip在各个时间点的频次
	 * 
	 * @param mid
	 * @param tstime
	 * @param isVIP
	 * @param freq
	 */
	public void putVIPForTweet(String mid, long tstime, boolean isVIP, int freq) {
		String cql = String.format("update %s set freq = freq + %d where mid='%s' and tstime = %d and isvip=%b",
				VIP_DIST, freq, mid, tstime, isVIP);
		execute(cql);
	}

	public Map<Boolean, Integer> getVIPForTweet(String mid, long start, long end) {
		Map<Boolean, Integer> counter = DefaultedPutMap.decorate(new HashMap<Boolean, Integer>(), new Factory() {
			@Override
			public Object create() {
				return 0;
			}
		});

		ResultSet set = conn
				.query(String.format("select isvip, freq from %s where mid = '%s' and tstime>= %d and tstime <= %d",
						VIP_DIST, mid, start, end));
		for (Row row : set.all()) {
			counter.put(row.getBool(0), counter.get(row.getBool(0)) + (int) row.getLong(1));
		}
		return counter;
	}

	/**
	 * 为每条原始微博mid，统计它的转发用户的loc在各个时间点的频次
	 * 
	 * @param mid
	 * @param tstime
	 * @param isVIP
	 * @param freq
	 */
	public void putClientForTweet(String mid, long tstime, String client, int freq) {
		BoundStatement stmt = new BoundStatement(clientStmt);

		stmt.setLong(0, freq);
		stmt.setString(1, mid);
		stmt.setLong(2, tstime);
		stmt.setString(3, client);

		executeStmt(stmt);
	}

	public Map<String, Integer> getClientForTweet(String mid, long start, long end) {
		Map<String, Integer> counter = DefaultedPutMap.decorate(new HashMap<String, Integer>(), new Factory() {
			@Override
			public Object create() {
				return 0;
			}
		});
		ResultSet set = conn
				.query(String.format("select client, freq from %s where mid = '%s' and  tstime>= %d and tstime <= %d",
						CLIENT_DIST, mid, start, end));
		for (Row row : set.all()) {
			counter.put(row.getString(0), (int) row.getLong(1));
		}
		return counter;
	}

	/**
	 * 为每条原始微博mid，统计它的转发用户的loc在各个时间点的频次
	 * 
	 * @param mid
	 * @param tstime
	 * @param isVIP
	 * @param freq
	 */
	public void putLocForTweet(String mid, long tstime, String loc, int freq) {
		String cql = String.format("update %s set freq = freq + %d where mid='%s' and tstime = %d and location='%s'",
				LOCATION_DIST, freq, mid, tstime, loc);
		execute(cql);
	}

	public Map<String, Integer> getLocForTweet(String mid, long start, long end) {
		Map<String, Integer> counter = DefaultedPutMap.decorate(new HashMap<String, Integer>(), new Factory() {
			@Override
			public Object create() {
				return 0;
			}
		});
		ResultSet set = conn
				.query(String.format("select location, freq from %s where mid = '%s' and tstime>= %d and tstime <= %d",
						LOCATION_DIST, mid, start, end));
		for (Row row : set.all()) {
			counter.put(row.getString(0), (int) row.getLong(1));
		}
		return counter;
	}

	/**
	 * 为每条原始微博mid，统计它的转发用户的loc在各个时间点的频次
	 * 
	 * @param mid
	 * @param tstime
	 * @param isVIP
	 * @param freq
	 */
	public void putMoodForTweet(String mid, long tstime, String mood, int freq) {
		String cql = String.format("update %s set freq = freq + %d where mid='%s' and tstime = %d and mood='%s'",
				MOOD_DIST, freq, mid, tstime, mood);
		execute(cql);
	}

	public Map<String, Integer> getMoodForTweet(String mid, long start, long end) {
		Map<String, Integer> counter = DefaultedPutMap.decorate(new HashMap<String, Integer>(), new Factory() {
			@Override
			public Object create() {
				return 0;
			}
		});
		ResultSet set = conn
				.query(String.format("select mood, freq from %s where mid = '%s' and tstime>= %d and tstime <= %d",
						MOOD_DIST, mid, start, end));
		for (Row row : set.all()) {
			counter.put(row.getString(0), (int) row.getLong(1));
		}
		return counter;
	}
}
