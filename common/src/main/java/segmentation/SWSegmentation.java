package segmentation;

import java.util.ArrayList;
import java.util.List;

import util.Pair;

public class SWSegmentation extends ISegmentation {
	List<Pair<Integer, Integer>> points = new ArrayList<Pair<Integer, Integer>>();
	int sum = 0;
	Interval pre;
	float max_error;
	private long mid;

	public SWSegmentation(long mid, float max_error, Interval pre, ISegSubscriber sub) {
		super(sub);
		this.pre = pre;
		this.max_error = max_error;
		this.mid = mid;
	}

	int lastTry = 0;

	@Override
	public void advance(int point, int value) {
		lastTry = points.size() - 1;
		if (points.isEmpty()) {
			points.add(new Pair<Integer, Integer>(point, value));
			return;
		}
		int missedPoint = points.get(points.size() - 1).getKey() + 1;
		for (; missedPoint < point; missedPoint++) {
			points.add(new Pair<Integer, Integer>(missedPoint, 0));
		}
		points.add(new Pair<Integer, Integer>(point, value));
		go(false);
	}

	/**
	 * 将当前所有的points用一个segment近似
	 */
	public void finish() {
		go(true);
	}

	/**
	 * 加入一个新的点
	 * 
	 * @param point
	 * @param value
	 * @return
	 */
	public void go(boolean finish) {
		boolean fakePoint = false;
		do {
			if (points.size() == 1 && finish) {
				end(0, 0);
				break;
			}

			while (lastTry + 1 < points.size()) {
				if (newSeg(lastTry + 1, fakePoint)) {
					lastTry = 0;
				} else {
					lastTry++;
				}
			}
			if (points.size() == 1 && points.get(0).getValue() == 0) {
				points.clear();
				break;
			}
			if (finish && !points.isEmpty()) {
				lastTry = points.size() - 1;
				fakePoint = true;
				points.add(new Pair<Integer, Integer>(points.get(points.size() - 1).getKey() + 1, 0));
			}
		} while (finish && !points.isEmpty());
	}

	/**
	 * 判断0-endIdx是否能够生成一个seg
	 * 
	 * @param endIdx
	 */
	private boolean newSeg(int endIdx, boolean finish) {
		Pair<Integer, Integer> start = points.get(0);
		Pair<Integer, Integer> last = points.get(endIdx);
		float k = (last.getValue() - start.getValue()) / ((float) (last.getKey() - start.getKey()));

		float error = 0;
		int i = 1;
		for (; i < endIdx; i++) {
			Pair<Integer, Integer> cur = points.get(i);
			error += Math.abs(k * (cur.getKey() - start.getKey()) + start.getValue() - cur.getValue());
		}
		if (error >= max_error) {
			if (error >= 2 * max_error)
				end(0, endIdx - 1);
			else
				end(0, endIdx);
			return true;
		} else if (finish) {
			end(0, endIdx);
		}
		return false;
	}

	public void end(int startIdx, int end) {
		Pair<Integer, Integer> start = points.get(startIdx);
		Pair<Integer, Integer> last = points.get(end);
		Segment seg = new Segment(start.getKey(), start.getValue(), last.getKey(), last.getValue());
		points.subList(startIdx, end).clear();

		newSeg(pre, seg);

		if (pre == null) {
			pre = new Interval(mid, seg.getStart(), seg.getEndTime(), seg.getValue());
		} else {
			pre.setEnd(end);
			pre.setAggValue(pre.getAggValue() + seg.getValue() - seg.getStartCount());
		}
	}
}
