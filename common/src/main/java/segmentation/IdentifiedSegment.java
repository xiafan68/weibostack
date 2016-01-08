package segmentation;

public class IdentifiedSegment extends Segment {
	long mid;

	public IdentifiedSegment(long mid, int start, int count, int endTime,
			int endCount) {
		super(start, count, endTime, endCount);
		this.mid = mid;
	}

	/**
	 * @return the mid
	 */
	public long getMid() {
		return mid;
	}

	/**
	 * @param mid
	 *            the mid to set
	 */
	public void setMid(long mid) {
		this.mid = mid;
	}

	@Override
	public String toString() {
		return "IdentifiedSegment [mid=" + mid + "," + super.toString() + "]";
	}
}
