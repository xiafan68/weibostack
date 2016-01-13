package weibo.rttree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import ds.tree.trie.Node;
import weibo.Tweet;

public class TweetNode extends Node<Tweet> implements Comparable<TweetNode> {

	public TweetNode(int level, Tweet value, Node<Tweet> parent) {
		super(level, value, parent);
	}

	@Override
	public Iterator<Node<Tweet>> iterator() {
		return new SortedNodeIterator(this, children.values());
	}

	public static class SortedNodeIterator implements Iterator<Node<Tweet>> {
		Node<Tweet> curNode;
		ArrayList<TweetNode> children;
		Iterator<TweetNode> iter;

		public SortedNodeIterator(Node<Tweet> curNode,
				Collection<Node<Tweet>> children) {
			this.curNode = curNode;
			TreeSet<TweetNode> set = new TreeSet<TweetNode>();
			for (Node<Tweet> node : children) {
				set.add((TweetNode) node);
			}
			this.children = new ArrayList<TweetNode>(set);
			this.iter = this.children.iterator();
		}

		@Override
		public TweetNode next() {
			return iter.next();
		}

		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public boolean equals(Object other) {
		return value.equals(((TweetNode) other).value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public int compareTo(TweetNode arg0) {
		return value.compareTo(arg0.value);
	}

}
