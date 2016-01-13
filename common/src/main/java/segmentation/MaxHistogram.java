package segmentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import util.Pair;

/**
 * 
 * @author xiafan
 * 
 */
public class MaxHistogram {

	private List<Integer> boundaries = new ArrayList<Integer>();
	private List<Float> heights = new ArrayList<Float>();

	public boolean isEmpty() {
		return boundaries.isEmpty();
	}

	public void addWindow(int start, int end, float value) {
		if (boundaries.size() > 0) {
			boundaries.add(end);
		} else {
			boundaries.add(start);
			boundaries.add(end);
		}

		heights.add(value);
	}

	public static MaxHistogram constructBySegs(List<Segment> segs) {
		int k = (int) Math.log(segs.size());
		if (k <= 1)
			return new MaxHistogram();
		int start = segs.get(0).getStart();
		SegsMaxHistConstruction1 con = new SegsMaxHistConstruction1(start, segs, k);
		return con.hist();
	}

	public static MaxHistogram constructByPoints(List<Segment> segs) {

		int width = segs.get(segs.size() - 1).getEndTime() - segs.get(0).getStart() + 1;
		if (width < 16)
			return null;
		int k = (int) (Math.log(width) / Math.log(4));

		List<Float> data = new ArrayList<Float>();
		Segment seg = null;
		data.add((float) segs.get(0).getStartCount());
		for (int i = 0; i < segs.size(); i++) {
			seg = segs.get(i);
			int cur = seg.getStart() + 1;
			while (cur <= seg.getEndTime()) {
				data.add(seg.getValue(cur));
				cur++;
			}
		}

		return construct(segs.get(0).getStart(), data, k);
	}

	public static MaxHistogram construct(int start, List<Float> points, int k) {
		OptimizedMaxHist con = new OptimizedMaxHist(start, points, k);
		return con.hist();
	}

	public float score(int start, int end) {
		float sum = 0;
		int idx = Collections.binarySearch(boundaries, start);
		if (idx < 0) {
			idx = Math.abs(idx + 1);
		} else {
			idx++;
		}
		for (int i = idx - 1; i < heights.size(); i++) {
			if (end < boundaries.get(i))
				break;
			else if (boundaries.get(i + 1) <= end) {
				sum += heights.get(i) * (boundaries.get(i + 1) - start);
			} else {
				sum += heights.get(i) * (end - start + 1);
			}
			start = boundaries.get(i + 1);
		}
		return sum;
	}

	public void write(StringBuffer buf) throws IOException {
		if (boundaries.size() == 0)
			return;
		buf.append(boundaries.get(0));
		for (int i = 1; i < boundaries.size(); i++) {
			buf.append("|");
			buf.append(boundaries.get(i));
		}
		buf.append("#");
		buf.append(heights.get(0));
		for (int i = 1; i < heights.size(); i++) {
			buf.append("|");
			buf.append(heights.get(i));
		}
	}

	public void read(String data) throws IOException {
		String[] fields = data.split("#");
		String[] bStr = fields[0].split("\\|");
		for (String b : bStr) {
			boundaries.add(Integer.parseInt(b));
		}

		String[] hStr = fields[1].split("\\|");
		for (String h : hStr) {
			heights.add(Float.parseFloat(h));
		}
	}

	/**
	 * 采用自底向上的动态规划构造
	 * 
	 * @author xiafan
	 * 
	 */
	public static class OptimizedMaxHist {
		private int startTime;
		private List<Float> points;
		private int k;
		// 记录points在所有k下面的最优值
		Pair<Float, List<Integer>>[] opts;
		// 所有endpoint在分割成某个k的时候的最优值
		Pair<Float, List<Integer>>[] curOpt;

		public OptimizedMaxHist(int start, List<Float> points, int k) {
			super();
			this.startTime = start;
			this.points = points;
			this.k = Math.min(points.size(), k);
			opts = new Pair[this.k];
			curOpt = new Pair[points.size()];
		}

		/**
		 * 
		 * @param curPoint
		 *            当前点
		 * @param err
		 *            从最后一个点到当前点之后的err和最大值
		 */
		public void updateErr(int curPoint, int end, Pair<Float, Float> err) {
			if (points.get(curPoint) > err.getKey()) {
				err.setValue((points.get(curPoint) - err.getKey()) * (end - curPoint) + err.getValue());
				err.setKey(points.get(curPoint));
			} else {
				err.setValue(err.getKey() - points.get(curPoint) + err.getValue());
			}
		}

		private void init() {
			// init[i]记录的是0-i之间的最大值以及误差和
			List<Integer> idx = new ArrayList<Integer>();
			idx.add(-1);
			curOpt[0] = new Pair<Float, List<Integer>>(0f, idx);
			float curMax = points.get(0);
			for (int i = 1; i < points.size(); i++) {
				idx = new ArrayList<Integer>();
				idx.add(-1);
				curOpt[i] = new Pair<Float, List<Integer>>(0f, idx);
				if (points.get(i) > curMax) {
					curOpt[i].setKey(curOpt[i - 1].getKey() + (points.get(i) - curMax) * i);
					curMax = points.get(i);
				} else {
					curOpt[i].setKey(curOpt[i - 1].getKey() + (curMax - points.get(i)));
				}
			}
			opts[0] = curOpt[points.size() - 1];
		}

		public void construct() {
			System.out.println("p size:" + points.size() + " k " + k);
			init();
			int end = points.size() - 1;
			for (int ck = 1; ck < k; ck++) {
				System.out.println("iter " + ck);
				for (int last = end; last >= ck; last--) {
					curOpt[last] = new Pair<Float, List<Integer>>(Float.MAX_VALUE, new ArrayList<Integer>());
					Pair<Float, Float> err = new Pair<Float, Float>(points.get(last), 0f);

					int idx = last - 1;
					for (int curStart = last - 1; curStart >= ck - 1; curStart--) {
						updateErr(curStart + 1, last, err);
						float curErr = curOpt[curStart].getKey() + err.getValue();
						if (curErr < curOpt[last].getKey()) {
							idx = curStart;
							curOpt[last].setKey(curErr);
						}
					}
					curOpt[last].getValue().addAll(curOpt[idx].getValue());
					curOpt[last].getValue().add(idx);
				}
				opts[ck] = curOpt[end];
			}
		}

		public MaxHistogram hist() {
			construct();
			int chosenK = 0;
			float minErr = Float.MAX_VALUE;

			for (int i = 0; i < opts.length; i++) {
				if (opts[i].getKey() < minErr)
					chosenK = i;
			}
			List<Integer> splitters = opts[chosenK].getValue();
			splitters.remove(0);
			// recover the splitters
			MaxHistogram ret = new MaxHistogram();
			int pre = 0;
			Iterator<Integer> iter = splitters.iterator();
			while (iter.hasNext()) {
				int splitter = iter.next();
				ret.addWindow(startTime + pre, startTime + splitter + 1, getMax(pre, splitter));
				pre = splitter + 1;
			}
			ret.addWindow(startTime + pre, startTime + points.size(), getMax(pre, points.size() - 1));
			return ret;
		}

		private float getMax(int start, int end) {
			float max = points.get(start);
			for (int i = start + 1; i <= end; i++) {
				if (max < points.get(i))
					max = points.get(i);
			}
			return max;
		}
	}

	/**
	 * 采用自底向上的动态规划构造
	 * 
	 * @author xiafan
	 * 
	 */
	public static class MaxHistConstruction {
		private int startTime;
		private List<Float> points;
		private int k;
		// error state, record the error for the interval [key, end]
		// trace[i][j]: records the maximal error during [i,j], also record the
		// previous split index
		Pair<Float, Integer>[][] trace;
		// errs[i][j]:记录[i,j]之间的最大值，以及把[i,j]分成1个桶时的代价
		Pair<Float, Float>[][] errs;

		public MaxHistConstruction(int start, List<Float> points, int k) {
			super();
			this.startTime = start;
			this.points = points;
			this.k = Math.min(points.size(), k);
			trace = new Pair[k][points.size()];
			errs = new Pair[points.size()][points.size()];
		}

		private void init() {
			// init[i]记录的是0-i之间的最大值以及误差和
			for (int i = 0; i < points.size(); i++) {
				errs[i][i] = new Pair<Float, Float>(0f, points.get(i));
			}
			for (int i = 0; i < points.size() - 1; i++) {
				for (int j = i + 1; j < points.size(); j++) {
					errs[i][j] = new Pair<Float, Float>();
					if (points.get(j) > errs[i][j - 1].getValue()) {
						errs[i][j].setKey(
								(points.get(j) - errs[i][j - 1].getValue()) * (j - i) + errs[i][j - 1].getKey());
						errs[i][j].setValue(points.get(j));
					} else {
						errs[i][j].setKey(errs[i][j - 1].getValue() - points.get(j) + errs[i][j - 1].getKey());
						errs[i][j].setValue(errs[i][j - 1].getValue());
					}
				}
			}

			for (int i = 0; i < k; i++) {
				trace[i][i] = new Pair<Float, Integer>(0f, i - 1);
			}

			for (int last = 1; last < points.size(); last++) {
				trace[0][last] = new Pair<Float, Integer>(getError(0, last), -1);
			}
		}

		/**
		 * 将[start, end]分层一组时的代价
		 * 
		 * @param start
		 * @param end
		 * @return
		 */
		private float getError(int start, int end) {
			float ret = 0f;
			if (start >= end)
				return ret;
			return errs[start][end].getKey();
		}

		private float getMax(int start, int end) {
			return errs[start][end].getValue();
		}

		public void construct() {
			init();
			for (int ck = 1; ck < k; ck++) {
				for (int last = ck; last < points.size(); last++) {
					trace[ck][last] = new Pair<Float, Integer>(Float.MAX_VALUE, -1);
					for (int curStart = ck - 1; curStart < last; curStart++) {
						float curErr = trace[ck - 1][curStart].getKey() + getError(curStart + 1, last);
						if (curErr < trace[ck][last].getKey()) {
							trace[ck][last].setKey(curErr);
							trace[ck][last].setValue(curStart);
						}
					}
				}
			}
		}

		public MaxHistogram hist() {
			construct();
			int chosenK = 0;
			float minErr = Float.MAX_VALUE;
			int j = points.size() - 1;

			for (int i = 0; i < k; i++) {
				if (trace[i][j].getKey() < minErr) {
					chosenK = i;
					minErr = trace[i][j].getKey();
				}
			}
			System.out.println("non optimized" + minErr);

			Stack<Pair<Float, Integer>> stack = new Stack<Pair<Float, Integer>>();

			for (int i = chosenK; i > 0; i--) {
				stack.push(trace[i][j]);
				j = stack.peek().getValue();
			}

			// recover the splitters
			MaxHistogram ret = new MaxHistogram();
			int pre = 0;
			while (!stack.isEmpty()) {
				Pair<Float, Integer> cur = stack.pop();
				ret.addWindow(startTime + pre, startTime + cur.getValue() + 1, getMax(pre, cur.getValue()));
				pre = cur.getValue() + 1;
			}
			ret.addWindow(startTime + pre, startTime + points.size(), getMax(pre, points.size() - 1));
			return ret;
		}
	}

	/**
	 * 采用自底向上的动态规划构造
	 * 
	 * @author xiafan
	 * 
	 */
	public static class SegsMaxHistConstruction {
		private int start;
		private List<Segment> segs;
		private int k;
		// error state, record the error for the interval [key, end]
		Pair<Float, Integer>[][][] errs;

		public SegsMaxHistConstruction(int start, List<Segment> segs, int k) {
			super();
			this.start = start;
			this.segs = segs;
			this.k = k;
			errs = new Pair[k][segs.size()][segs.size() + 1];
		}

		public void init() {
			for (int i = 0; i < segs.size(); i++) {
				Segment seg = segs.get(i);
				float max = seg.getStartCount();
				int width = 0;
				for (int j = i; j < segs.size(); j++) {
					Segment curSeg = segs.get(j);
					float cur = curSeg.getEndCount();

					float preErr = 0.0f;
					if (i != j) {
						preErr = errs[0][i][j].getKey();
					}
					int curWidth = curSeg.getEndTime() - curSeg.getStart() - 1;
					if (cur > max) {
						errs[0][i][j + 1] = new Pair<Float, Integer>(preErr + (cur - max) * width + curWidth * cur
								- curSeg.getValue() + curSeg.getStartCount() + curSeg.getEndCount(), j + 1);
						max = cur;
					} else {
						errs[0][i][j + 1] = new Pair<Float, Integer>(preErr + curWidth * max - curSeg.getValue()
								+ curSeg.getStartCount() + curSeg.getEndCount(), j + 1);
					}
					width += curWidth;
				}
			}
		}

		public void construct() {
			init();
			for (int iter = 1; iter < k; iter++)
				for (int i = 0; i < segs.size(); i++) {
					for (int j = i + iter + 1; j <= segs.size(); j++) {
						float minErr = Float.MAX_VALUE;
						int minIdx = j + 1;
						for (int m = i + iter; m < j; m++) {
							float err = errs[iter - 1][i][m].getKey() + errs[0][m][j].getKey();
							if (err < minErr) {
								minErr = err;
								minIdx = m;
							}
						}
						errs[iter][i][j] = new Pair<Float, Integer>(minErr, minIdx);
					}
				}
		}

		private float getPoint(int point) {
			if (point != segs.size())
				return segs.get(point).getStartCount();
			else
				return segs.get(point - 1).getEndCount();
		}

		private int getTime(int point) {
			if (point != segs.size())
				return segs.get(point).getStart();
			else
				return segs.get(point - 1).getEndTime();
		}

		public MaxHistogram hist() {
			construct();
			Stack<Pair<Float, Integer>> stack = new Stack<Pair<Float, Integer>>();
			int j = segs.size();
			for (int i = k - 1; i >= 0; i--) {
				stack.push(errs[i][0][j]);
				int tmp = stack.peek().getValue();
				stack.peek().setValue(j);
				j = tmp;
			}

			// recover the splitters
			MaxHistogram ret = new MaxHistogram();
			int pre = 0;
			while (!stack.isEmpty()) {
				Pair<Float, Integer> cur = stack.pop();
				float max = 0;
				for (int i = pre; i <= cur.getValue(); i++) {
					float val = getPoint(i);
					if (val > max) {
						max = val;
					}
				}
				ret.addWindow(start, getTime(cur.getValue()), max);

				pre = cur.getValue();
				start += cur.getValue();
			}
			return ret;
		}
	}

	/**
	 * 采用自底向上的动态规划构造
	 * 
	 * @author xiafan
	 * 
	 */
	public static class SegsMaxHistConstruction1 {
		private int startTime;
		private List<Segment> segs;
		private int k;
		// error state, record the error for the interval [key, end]
		Pair<Float, Integer>[][] errs;
		float[][] maxErr;

		public SegsMaxHistConstruction1(int start, List<Segment> segs, int k) {
			super();
			this.startTime = start;
			this.segs = segs;
			this.k = k;
			errs = new Pair[segs.size() + 1][k];
			maxErr = new float[segs.size() + 1][segs.size() + 1];
		}

		public void init() {
			for (int i = 0; i < segs.size(); i++) {
				Segment seg = segs.get(i);
				float max = seg.getStartCount();
				int width = 0;
				for (int j = i; j < segs.size(); j++) {
					Segment curSeg = segs.get(j);
					float cur = curSeg.getEndCount();

					float preErr = 0.0f;
					if (i != j) {
						preErr = maxErr[i][j];
					}
					int curWidth = curSeg.getEndTime() - curSeg.getStart() - 1;
					if (cur > max) {
						maxErr[i][j + 1] = preErr + (cur - max) * width + curWidth * cur - curSeg.getValue()
								+ curSeg.getStartCount() + curSeg.getEndCount();
						max = cur;
					} else {
						maxErr[i][j + 1] = preErr + curWidth * max - curSeg.getValue() + curSeg.getStartCount()
								+ curSeg.getEndCount();
					}
					width += curWidth;
				}
			}
			for (int i = 1; i < segs.size() + 1; i++) {
				errs[i][0] = new Pair<Float, Integer>(maxErr[0][i], i);
			}
		}

		public void construct() {
			init();
			for (int iter = 1; iter < k; iter++)
				for (int i = iter + 1; i < segs.size() + 1; i++) {
					float minErr = Float.MAX_VALUE;
					int minIdx = i;
					for (int m = iter; m < i; m++) {
						float err = errs[m][iter - 1].getKey() + maxErr[m][i];
						if (err < minErr) {
							minErr = err;
							minIdx = m;
						}
					}
					errs[i][iter] = new Pair<Float, Integer>(minErr, minIdx);
				}
		}

		private float getPoint(int point) {
			if (point != segs.size())
				return segs.get(point).getStartCount();
			else
				return segs.get(point - 1).getEndCount();
		}

		private int getTime(int point) {
			if (point != segs.size())
				return segs.get(point).getStart();
			else
				return segs.get(point - 1).getEndTime();
		}

		public MaxHistogram hist() {
			construct();
			Stack<Integer> stack = new Stack<Integer>();
			stack.add(segs.size());
			int j = segs.size();
			for (int i = k - 1; i > 0; i--) {
				stack.push(errs[j][i].getValue());
				j = stack.peek();
			}

			// recover the splitters
			MaxHistogram ret = new MaxHistogram();
			int pre = 0;
			while (!stack.isEmpty()) {
				Integer cur = stack.pop();
				float max = 0;
				for (int i = pre; i <= cur; i++) {
					float val = getPoint(i);
					if (val > max) {
						max = val;
					}
				}
				ret.addWindow(startTime, getTime(cur), max);

				pre = cur;
				startTime += cur;
			}
			return ret;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		try {
			write(buf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buf.toString();
	}

	public static void main(String[] args) throws IOException {

		List<Float> points = new ArrayList<Float>();
		int start = 100000;
		int size = 30000;
		for (int i = 0; i < size; i++) {
			points.add((float) i);
		}

		// System.out.println(construct(start, points, 10));
		long startTime = System.currentTimeMillis();
		OptimizedMaxHist cons = new OptimizedMaxHist(start, points, (int) (Math.log(size) / Math.log(4)));
		System.out.println("----------");
		System.out.println(cons.hist());
		System.out.println("time cost " + (System.currentTimeMillis() - startTime) / 1000.0);
		points = new ArrayList<Float>();
		for (int i = 19; i >= 0; i--) {
			points.add((float) i);
		}

		System.out.println(construct(start, points, 3));
		cons = new OptimizedMaxHist(start, points, 3);
		System.out.println("----------");
		System.out.println(cons.hist());

		points.clear();
		for (int i = 19; i >= 0; i--) {
			points.add((float) 2 * i + 1);
		}
		for (int i = 2; i < 10; i++) {
			points.add((float) i);
		}

		for (int i = 8; i >= 0; i--) {
			points.add((float) i);
		}
		for (int i = 1; i < 16; i++) {
			points.add((float) i);
		}
		MaxHistogram hist = construct(0, points, 100);
		System.out.println(hist + " " + 10 + " act " + hist.boundaries.size());
		System.out.println(hist.score(1, 15));
		cons = new OptimizedMaxHist(0, points, 100);
		System.out.println("----------");
		System.out.println(cons.hist());

		StringBuffer buf = new StringBuffer();
		hist.write(buf);
		MaxHistogram newHist = new MaxHistogram();
		newHist.read(buf.toString());
		System.out.println("deseriealize " + newHist);

		List<Segment> segs = new ArrayList<Segment>();
		segs.add(new Segment(0, 0, 10, 10));
		segs.add(new Segment(10, 10, 20, 0));
		segs.add(new Segment(20, 0, 30, 18));
		segs.add(new Segment(30, 18, 40, 0));
		segs.add(new Segment(40, 0, 50, 20));
		SegsMaxHistConstruction t = new SegsMaxHistConstruction(0, segs, 3);
		t.construct();
		System.out.println(t.hist());
		SegsMaxHistConstruction1 t1 = new SegsMaxHistConstruction1(0, segs, 3);
		t1.construct();
		System.out.println(t1.hist());
	}
}
