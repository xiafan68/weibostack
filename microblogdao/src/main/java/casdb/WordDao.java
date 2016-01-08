package casdb;

import java.util.Map;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;

/**
 * 单词相关的数据访问：词频， 热词
 * 
 * @author xiafan
 *
 */
public class WordDao {
	CassandraConn conn;
	PreparedStatement wordStmt;

	public WordDao(CassandraConn conn) {
		this.conn = conn;
		wordStmt = conn.prepare(
				String.format("update %s set freq = freq + ? where word=? and tstime = ?", DBUtil.WORD_FREQ_TABLE));
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
		Map<Long, Long> ret = null;
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
}
