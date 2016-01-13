package casdb;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

import util.Pair;


public class WordZScoreDao {

	CassandraConn conn;

	public WordZScoreDao(CassandraConn conn) {
		this.conn = conn;
	}

	public void store(String word, double freq, long timestamp) {
		conn.executeCQL(String.format("insert into wordzscore values('%s',%f,%d)", word, freq, timestamp));
	}

	public List<Pair<String, Float>> queryPopWordsByTime(long timestamp) {
		List<Pair<String, Float>> ret = new ArrayList<Pair<String, Float>>();
		ResultSet set = conn.query(
				String.format("select word, zscore from wordzscore where timestamp = %d order by zscore", timestamp));

		for (Row row : set.all()) {
			ret.add(new Pair<String, Float>(row.getString(0), row.getFloat(1)));
		}
		return ret;
	}

	public void close() {
		conn.close();
	}

	public static WordZScoreDao create(String node) {
		CassandraConn conn = new CassandraConn();
		conn.connect(node);
		return new WordZScoreDao(conn);
	}
}
