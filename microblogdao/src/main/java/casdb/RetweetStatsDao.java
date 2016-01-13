package casdb;

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

	public RetweetStatsDao(CassandraConn conn) {
		this.conn = conn;
	}

	/**
	 * 初始化retweet统计相关的量
	 * 
	 * @param conn
	 */
	public static void init(CassandraConn conn) {
		conn.executeCQL(String.format(
				"create table %s (mid text, tstime bigint, isMale bool, freq counter, primary key(text, tstime))",
				GENDER_DIST));
		conn.executeCQL(String.format(
				"create table %s (mid text, tstime bigint, isVIP bool, freq counter, primary key(text, tstime))",
				VIP_DIST));
		conn.executeCQL(String.format(
				"create table %s (mid text, tstime bigint, client text, freq counter, primary key(text, tstime))",
				CLIENT_DIST));

		conn.executeCQL(String.format(
				"create table %s (mid text, tstime bigint, location text, freq counter, primary key(text, tstime))",
				LOCATION_DIST));
		conn.executeCQL(String.format(
				"create table %s (mid text, tstime bigint, mood text, freq counter, primary key(text, tstime))",
				MOOD_DIST));
	}

	/**
	 * 为每条原始微博mid，统计它的转发用户的性别在各个时间点的频次
	 * 
	 * @param mid
	 * @param timestamp
	 * @param freq
	 */
	public void putGenderForTweet(String mid, long timestamp, boolean isMale, int freq) {

	}

}
