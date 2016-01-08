package segmentation;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Interval {
	public static final int MAX_VALUE = Integer.MAX_VALUE;
	// Segment newSeg = null;
	float aggValue;
	long mid;
	int start;
	int end;
	MaxHistogram hist;

	public Interval(long mid, int start, int end, float value) {
		super();
		this.mid = mid;
		this.start = start;
		this.end = end;
		this.aggValue = value;
	}

	public Interval(long mid, int start, int end, float value, MaxHistogram hist) {
		super();
		this.mid = mid;
		this.start = start;
		this.end = end;
		this.aggValue = value;
		this.hist = hist;
	}

	public MaxHistogram getHist() {
		return hist;
	}

	public void setHist(MaxHistogram hist) {
		this.hist = hist;
	}

	/**
	 * @return the preValue
	 */
	public float getAggValue() {
		return aggValue;
	}

	/**
	 * @param preValue
	 *            the preValue to set
	 */
	public void setAggValue(float aggValue) {
		this.aggValue = aggValue;
	}

	/**
	 * @return the mid
	 */
	public long getMid() {
		return mid;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public int getEnd() {
		return end;
	}

	public int getWidth() {
		return end - start;
	}

	/**
	 * @param mid
	 *            the mid to set
	 */
	public void setMid(long mid) {
		this.mid = mid;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(int end) {
		this.end = end;
	}

	public boolean contains(Interval other) {
		if (start <= other.getStart() && end >= other.getEnd()) {
			return true;
		}
		return false;
	}

	public boolean contains(int point) {
		if (start <= point && point <= end)
			return true;
		return false;
	}

	public boolean isFull() {
		return start != Integer.MIN_VALUE && end != Integer.MAX_VALUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Interval [aggValue=" + aggValue + ", mid=" + mid + ", start="
				+ start + ", end=" + end + "]";
	}

	@Override
	public boolean equals(Object other) {
		Interval inv = (Interval) other;
		return mid == inv.mid && aggValue == inv.getAggValue();
	}

	public void write(DataOutput output) throws IOException {
		output.writeLong(mid);
		output.writeInt(start);
		output.writeInt(end);
		output.writeFloat(aggValue);
	}

	public void read(DataInput input) throws IOException {
		mid = input.readLong();
		start = input.readInt();
		end = input.readInt();
		aggValue = input.readFloat();
	}
}
