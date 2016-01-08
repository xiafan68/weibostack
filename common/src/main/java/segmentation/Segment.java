package segmentation;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Comparator;

public class Segment {
	protected int start;
	protected int count;
	protected int endTime;
	protected int endCount;

	// transient float k = 0.0f;

	public Segment() {

	}

	public Segment(Segment o) {
		super();
		this.start = o.start;
		this.count = o.count;
		this.endTime = o.endTime;
		this.endCount = o.endCount;
	}

	public Segment(int start, int count, int endTime, int endCount) {
		super();
		this.start = start;
		this.count = count;
		this.endTime = endTime;
		this.endCount = endCount;
	}

	public float getValue() {
		long gap = endTime - start + 1;
		// int sum = (int) ((gap + 1) * count + (gap * (gap + 1) / 2.0f) * k);
		/*
		 * for (long i = start; i <= endTime; i++) { sum += (k * (i - start) +
		 * count); }
		 */
		float sum = (count + endCount) * gap / 2.0f;
		return sum;
	}

	public float getValue(int x, int y) {
		if (y < start || x > endTime)
			return 0;
		int startIdx = Math.max(x, start);
		int endIdx = Math.min(y, endTime);
		long gap = endIdx - startIdx + 1;
		// int sum = (int) ((gap + 1) * count + (endIdx * (endIdx + 1) / 2.0f -
		// startIdx
		// * (startIdx + 1) / 2.0f)
		// * k);
		float sum = (getValue(startIdx) + getValue(endIdx)) * gap / 2.0f;
		return sum;
	}

	public float getValue(int x) {
		if (start == endTime) {
			if (x < start)
				return 0;
			else
				return count;
		} else
			return ((count - endCount) * (x - start)) / (float) (start - endTime) + count;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @return the count
	 */
	public int getStartCount() {
		return count;
	}

	/**
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * @return the endCount
	 */
	public int getEndCount() {
		return endCount;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	/**
	 * @param endCount
	 *            the endCount to set
	 */
	public void setEndCount(int endCount) {
		this.endCount = endCount;
	}

	public void write(DataOutput output) throws IOException {
		output.writeInt(start);
		output.writeInt(count);
		output.writeInt(endTime);
		output.writeInt(endCount);
	}

	public void read(DataInput input) throws IOException {
		start = input.readInt();
		count = input.readInt();
		endTime = input.readInt();
		endCount = input.readInt();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// return "Segment [start=" + start + ", count=" + count + ", endTime="
		// + endTime + ", endCount=" + endCount + "]";
		return start + "_" + count + "_" + endTime + "_" + endCount;
	}

	public boolean parse(String row) {
		String[] fields = row.split("_");
		if (fields.length >= 4) {
			start = Integer.parseInt(fields[0]);
			count = Integer.parseInt(fields[1]);
			endTime = Integer.parseInt(fields[2]);
			endCount = Integer.parseInt(fields[3]);
			// k = (count - endCount) / (float) (start - endTime);
			return true;
		}
		return false;
	}

	private static class SegComparator implements Comparator<Segment> {

		@Override
		public int compare(Segment arg0, Segment arg1) {
			int comp = arg0.getStart() - arg1.getStart();
			if (comp > 0) {
				return 1;
			} else if (comp == 0) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	public static int size() {
		return Integer.SIZE * 4 / 8;
	}

	/*
	 * @Override public int compareTo(Segment o) { if (start > o.start) { return
	 * -1; } else if (start == o.start) { return 0; } else { return 1; } }
	 */
}
