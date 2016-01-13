package collection;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import util.Pair;

public class CollectionUtils {
	private static class MergeIterator<V> implements Iterator<V> {
		PriorityQueue<Pair<V, Iterator<V>>> queue = new PriorityQueue<Pair<V, Iterator<V>>>();

		public MergeIterator(List<Iterator<V>> iters, final Comparator<V> comp) {
			queue = new PriorityQueue<Pair<V, Iterator<V>>>(10, new Comparator<Pair<V, Iterator<V>>>() {
				@Override
				public int compare(Pair<V, Iterator<V>> o1, Pair<V, Iterator<V>> o2) {
					return comp.compare(o1.getKey(), o2.getKey());
				}
			});
			for (Iterator<V> iter : iters) {
				if (iter.hasNext()) {
					queue.offer(new Pair<V, Iterator<V>>(iter.next(), iter));
				}
			}
		}

		@Override
		public boolean hasNext() {
			return !queue.isEmpty();
		}

		@Override
		public V next() {
			Pair<V, Iterator<V>> cur = queue.poll();
			V ret = cur.getKey();
			if (cur.getValue().hasNext()) {
				cur.setKey(cur.getValue().next());
				queue.offer(cur);
			}
			return ret;
		}

		@Override
		public void remove() {
			throw new RuntimeException("remove is not supported by MergeIterator");
		}

	}

	public static <V extends Comparable<V>> Iterator<V> merge(List<Iterator<V>> iters, final Comparator<V> comp) {
		return new MergeIterator<V>(iters, comp);
	}
}
