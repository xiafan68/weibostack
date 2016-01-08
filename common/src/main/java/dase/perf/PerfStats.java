package dase.perf;

import java.util.HashMap;

public class PerfStats {
	HashMap<String, OpStats> ops = new HashMap<String, OpStats>();

	public void start(String op) {
		if (!ops.containsKey(op)) {
			ops.put(op, new OpStats(op));
		}
		ops.get(op).start();
	}

	public void end(String op) {
		ops.get(op).end();
	}

	public void reset() {
		ops.clear();
	}

	@Override
	public String toString() {
		return "PerfStats [ops=" + ops + "]";
	}

	private class OpStats {
		public String op;
		public int opCount = 0;
		public long latencySum = 0;

		public OpStats(String op) {
			this.op = op;
		}

		long start = 0;

		public void start() {
			start = System.currentTimeMillis();
		}

		public void end() {
			latencySum += System.currentTimeMillis() - start;
			opCount++;
		}

		public void reset() {
			opCount = 0;
			latencySum = 0;
		}

		@Override
		public String toString() {
			return "OpStats [op=" + op + ", opCount=" + opCount + ", latency="
					+ latencySum + "average:" + latencySum / (opCount + 1.0)
					+ "]";
		}

	}
}
