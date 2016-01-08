package casdb;

import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

public class SegStateDao {
	CassandraConn conn;

	public SegStateDao(CassandraConn conn) {
		this.conn = conn;
	}

	public SegState getSegState(String mid) {
		ResultSet set = conn.query(String.format("select updatetime from segstate where mid = '%s'", mid));
		List<Row> rows = set.all();
		if (rows.size() != 0) {
			return new SegState(mid, rows.get(0).getLong(0));
		} else {
			return null;
		}
	}

	public void putSegState(SegState state) {

	}

	public static class SegState {
		public String mid = "";
		public long lastUpdateTime = 0;

		public SegState(String mid, long lastUpdateTime) {
			this.mid = mid;
			this.lastUpdateTime = lastUpdateTime;
		}
	}
}
