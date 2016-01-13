package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Histogram {

	TreeMap<Double, Integer> hist = new TreeMap<Double, Integer>();

	public void increment(long item) {
		Integer count = hist.get((double) item);
		if (hist.containsKey((double) item)) {
			count += 1;
		} else {
			count = 1;
		}
		hist.put((double) item, count);
	}

	public void decrement(long item) {
		if (hist.containsKey((double) item)) {
			if (hist.get((double) item) == 1) {
				hist.remove((double) item);
			} else {
				hist.put((double) item, hist.get((double) item) - 1);
			}
		}
	}

	public Iterator<Entry<Double, Integer>> iterator() {
		return hist.entrySet().iterator();
	}

	public int size() {
		return hist.size();
	}

	/**
	 * 计算最后一个key和第一个key的差值，也就是histogram的宽度
	 * 
	 * @return
	 */
	public long width() {
		if (hist.size() <= 1) {
			return 0;
		} else {
			return (long) (hist.lastEntry().getKey() - hist.firstEntry()
					.getKey());
		}
	}

	public int total() {
		int ret = 0;
		for (Integer count : hist.values()) {
			ret += count;
		}
		return ret;
	}

	public void merge(Histogram other) {
		for (Entry<Double, Integer> entry : other.hist.entrySet()) {
			if (hist.containsKey(entry.getKey())) {
				hist.put(entry.getKey(),
						entry.getValue() + hist.get(entry.getKey()));
			} else {
				hist.put(entry.getKey(), entry.getValue());
			}
		}
	}

	public int aggValue(long start, long end) {
		Iterator<Integer> iter = hist
				.subMap((double) start, true, (double) end, true).values()
				.iterator();
		int count = 0;
		while (iter.hasNext()) {
			count += iter.next();
		}
		return count;
	}

	public static final String FIELD_SEP = ":";
	public static final String BUCKET_SEP = "|";

	@Override
	public String toString() {
		double preItem = 0;
		double delta = 0;
		StringBuffer buf = new StringBuffer();
		for (Entry<Double, Integer> entry : hist.entrySet()) {
			// 应该可以减少一些存储
			delta = entry.getKey() - preItem;
			preItem = entry.getKey();
			buf.append(delta);
			buf.append(FIELD_SEP);
			buf.append(entry.getValue());
			buf.append(BUCKET_SEP);
		}
		if (buf.length() > 0)
			return buf.substring(0, buf.length() - BUCKET_SEP.length());
		else
			return buf.toString();
	}

	public void fromString(String text) {
		String[] buckets = text.split("\\" + BUCKET_SEP);
		double preItem = 0;
		for (String bucket : buckets) {
			if (!bucket.isEmpty()) {
				String[] fields = bucket.split(FIELD_SEP);
				preItem += Double.parseDouble(fields[0]);
				hist.put(preItem, Integer.parseInt(fields[1]));
			}
		}
	}

	/**
	 * 
	 * @param granularity
	 *            统计的时间单元
	 * @return
	 */
	public List<Pair<Double, Integer>> toGNUPlot(int granularity) {
		long curWin = 1;
		int count = 0;
		List<Pair<Double, Integer>> ret = new ArrayList<Pair<Double, Integer>>();
		if (!hist.isEmpty()) {
			long win = 0;
			double start = hist.firstKey();
			for (Entry<Double, Integer> entry : hist.entrySet()) {
				win = (long) ((entry.getKey() - start) / granularity);
				if (win <= curWin - 1) {
					count += entry.getValue();
				} else {
					ret.add(new Pair<Double, Integer>((double) curWin, count));
					curWin = win + 1;
					count = entry.getValue();
				}
			}
			if (win != curWin) {
				ret.add(new Pair<Double, Integer>((double) curWin, count));
			}
		}
		return ret;
	}

	public Histogram groupby(int width) {
		long curWin = 1;
		int count = 0;
		Histogram ret = new Histogram();
		if (!hist.isEmpty()) {
			long win = 0;
			double start = hist.firstKey();
			for (Entry<Double, Integer> entry : hist.entrySet()) {
				win = entry.getKey().longValue() / width;
				if (ret.hist.containsKey((double) win)) {
					ret.hist.put((double) win, ret.hist.get((double) win)
							+ entry.getValue());
				} else {
					ret.hist.put((double) win, entry.getValue());
				}
				/*
				 * if (win <= curWin - 1) { count += entry.getValue(); } else {
				 * ret.hist.put((double) curWin, count); curWin = win + 1; count
				 * = entry.getValue(); }
				 */
			}
			// if (win <= curWin - 1) {
			// ret.hist.put((double) curWin, count);
			// }
		}
		return ret;
	}

	/**
	 * 将当前histogram压缩到0-range范围内
	 * 
	 * @param range
	 *            范围的上界
	 * @return
	 */
	public Histogram normalize(int range) {
		if (width() <= range)
			return this;
		else {
			float scale = ((float) range) / width();
			Histogram ret = new Histogram();
			for (Entry<Double, Integer> entry : hist.entrySet()) {
				ret.hist.put(entry.getKey() * scale, entry.getValue());
			}
			return ret;
		}
	}

	public Histogram toAcc() {
		Histogram ret = new Histogram();
		int sum = 0;
		for (Entry<Double, Integer> entry : hist.entrySet()) {
			sum += entry.getValue();
			ret.hist.put(entry.getKey(), sum);
		}
		return ret;
	}

	/**
	 * 
	 * @return 返回累积分布在20%,40%,60%,80%的时候的加速度
	 */
	public List<Float> accelerations() {
		int total = total();
		List<Float> ret = new ArrayList<Float>(4);

		Entry<Double, Integer> preEntry = null;
		float[] splits = new float[] { 0.2f, 0.4f, 0.6f, 0.8f };
		int[] counters = new int[] { 0, 0, 0, 0 };
		int sum = 0;
		for (Entry<Double, Integer> entry : hist.entrySet()) {
			int i = 0;
			sum += entry.getValue();
			for (float split : splits) {
				if (sum >= total * split) {
					counters[i]++;
					if (counters[i] == 1) {
						if (preEntry == null) {
							ret.add((float) (entry.getValue() / entry.getKey()));
						} else
							ret.add((entry.getValue() - preEntry.getValue())
									/ ((float) (entry.getKey() - preEntry
											.getKey())));
					}
				}
				i++;
			}
			preEntry = entry;
		}
		return ret;
	}

	/**
	 * 
	 * @return 返回累积分布在20%,40%,60%,80%的时候的加速度
	 */
	public List<Float> speeds() {
		int total = total();
		List<Float> ret = new ArrayList<Float>(4);

		float[] splits = new float[] { 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f,
				0.7f, 0.8f, 0.9f };
		int[] counters = new int[splits.length];
		Arrays.fill(counters, 0);

		int sum = 0;
		for (Entry<Double, Integer> entry : hist.entrySet()) {
			int i = 0;
			sum += entry.getValue();
			for (float split : splits) {
				if (sum >= total * split) {
					counters[i]++;
					if (counters[i] == 1) {
						ret.add((float) (entry.getValue()));
						break;
					}
				}
				i++;
			}
		}
		return ret;
	}

	public double speed(double point) {
		Entry<Double, Integer> celling = hist.ceilingEntry(point);
		Entry<Double, Integer> floor = hist.floorEntry(point);
		return (celling.getValue() - floor.getValue())
				/ (celling.getKey() - floor.getKey());
	}

	public static void main(String[] args) throws IOException {
		/*
		 * Histogram hist = new Histogram(); hist.increment(1000);
		 * hist.increment(100); hist.increment(10); hist.increment(1);
		 * hist.increment(2); String output = hist.toString();
		 * System.out.println(output); hist = new Histogram();
		 * hist.fromString(output); System.out.println(hist);
		 */

		
		  /*BufferedReader reader = new BufferedReader(new InputStreamReader( new
		 FileInputStream("D:\\test\\data\\longsegs.txt"))); //
		  DataOutputStream dos = new DataOutputStream(new FileOutputStream( //
		  "D:\\快盘\\学术\\论文\\story_telling\\fig\\tsdata.txt")); final
		  DataOutputStream sgDos = new DataOutputStream( new FileOutputStream(
		  "D:\\快盘\\学术\\论文\\story_telling\\fig\\segdata.txt")); String line =
		  null; while (null != (line = reader.readLine())) { Histogram hist =
		  new Histogram(); hist.fromString(line); SWSegmentation seg = new
		  SWSegmentation(0, 10, null, new ISegSubscriber() { boolean first =
		  true;
		  
		  @Override public void newSeg(Interval preInv, Segment seg) { try { if
		  (first) { first = false; sgDos.write(String.format("%d\t%d\n",
		  seg.getStart(), seg.getCount()) .getBytes()); }
		  System.out.println(seg); sgDos.write(String.format("%d\t%d\n",
		  seg.getEndTime(), seg.getEndCount()) .getBytes()); } catch
		  (IOException e) { // TODO Auto-generated catch block
		 e.printStackTrace(); } }
		  
		  }); for (Pair<Double, Integer> pair : hist.toGNUPlot(1000 * 60 * 60))
		  { // dos.write(String.format("%d\t%d\n", pair.arg0.longValue(), //
		  pair.arg1.intValue()).getBytes()); seg.advance(pair.arg0.longValue(),
		  pair.arg1); } seg.end();
		  
		  break; } reader.close(); // dos.close(); sgDos.close();
*/		 
	}

	public Pair<Integer, Integer> skew(int threshold) {
		int a = 0;
		int b = 0;
		for (Entry<Double, Integer> entry : hist.entrySet()) {
			if (entry.getValue() > threshold) {
				a += entry.getValue();
			} else {
				b += entry.getValue();
			}
		}
		return new Pair<Integer, Integer>(a, b);
	}
}
