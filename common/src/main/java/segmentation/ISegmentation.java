package segmentation;

public abstract class ISegmentation {
	ISegSubscriber sub;

	public ISegmentation(ISegSubscriber sub) {
		this.sub = sub;
	}

	public abstract void advance(int point, int value);

	protected void newSeg(Interval preInv, Segment seg) {
		sub.newSeg(preInv, seg);
	}

	public static interface ISegSubscriber {
		public void newSeg(Interval preInv, Segment seg);
	}
}
