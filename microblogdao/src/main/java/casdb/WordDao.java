package casdb;

import java.util.HashMap;
import java.util.Map;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

/**
 * 单词相关的数据访问：词频， 热词
 * 
 * @author xiafan
 *
 */
public class WordDao {
	CassandraConn conn;
	PreparedStatement wordStmt;
	PreparedStatement wordFreq;

	public WordDao(CassandraConn conn) {
		this.conn = conn;
		wordStmt = conn.prepare(
				String.format("update %s set freq = freq + ? where word=? and tstime = ?", DBUtil.WORD_FREQ_TABLE));
		wordFreq = conn.prepare(String.format(
				"select tstime, freq from %s where word = ? and tstime >= ? and tstime <= ?", DBUtil.WORD_FREQ_TABLE));
	}

	public void updateWordFreq(String word, long timestamp, long freq) {
		word = word.replaceAll("\"", "\\\"");
		word = word.replaceAll("'", "\\\'");
		BoundStatement stmt = new BoundStatement(wordStmt);
		stmt.setLong(0, freq);
		stmt.setString(1, word);
		stmt.setLong(2, timestamp);
		conn.execute(stmt);
	}

	public Map<Long, Long> getWordFreq(String word, long startTime, long endTime) {
		Map<Long, Long> ret = new HashMap<Long, Long>();
		BoundStatement stmt = new BoundStatement(wordFreq);
		stmt.setString(0, word);
		stmt.setLong(1, startTime);
		stmt.setLong(2, endTime);
		ResultSet set = conn.query(stmt);
		for (Row row : set.all()) {
			ret.put(row.getLong(0), row.getLong(1));
		}
		return ret;
	}

	public Map<String, Float> hotWords(long timestamp) {
		Map<String, Float> ret = null;
		return ret;
	}

	public Map<String, Float> degradingWords(long timestamp) {
		Map<String, Float> ret = null;
		return ret;
	}

	public static void main(String[] args) {
		CassandraConn conn = new CassandraConn();
		conn.connect(args[0]);
		// conn.executeCQL("select * from status limit 10");
		WordDao dao = new WordDao(conn);
		System.out.println(dao.getWordFreq("信我", 0, Long.MAX_VALUE));
		conn.close();
	}
}
