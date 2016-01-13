package ds.tree.trie;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import util.Pair;
import weibo.UncertainObject;

/**
 * 宽度优先遍历tree
 * 
 * @author xiafan
 *
 */
public class TreeBFSIterator<T extends UncertainObject> implements Iterator<Pair<T, T>> {
	TrieTree<T> tree;
	Queue<Pair<Node<T>, Iterator<Node<T>>>> stack = new LinkedList<Pair<Node<T>, Iterator<Node<T>>>>();

	public TreeBFSIterator(TrieTree<T> tree) {
		this.tree = tree;
		Node<T> root = tree.getRoot();
		if (!root.isLeaf() && root.degree() > 0)
			stack.offer(new Pair<Node<T>, Iterator<Node<T>>>(root, root.iterator()));
	}

	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	@Override
	public Pair<T, T> next() {
		while (!stack.isEmpty()) {
			if (stack.peek().getValue().hasNext()) {
				Node<T> child = stack.peek().getValue().next();
				if (!child.isLeaf() && child.degree() > 0) {
					stack.offer(new Pair<Node<T>, Iterator<Node<T>>>(child, child.iterator()));
				}
				T parent = stack.peek().getKey().getValue();
				if (!stack.peek().getValue().hasNext()) {
					stack.poll();
				}
				return new Pair<T, T>(parent, child.getValue());
			} else {
				stack.poll();
			}
		}
		return null;
	}

	@Override
	public void remove() {
		throw new RuntimeException("remove is not supported TreeIterator");
	}

	static class IntegerObj implements UncertainObject {
		int val;

		public IntegerObj(int val) {
			this.val = val;
		}

		@Override
		public boolean certain() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void reconcile(UncertainObject other) {
			// TODO Auto-generated method stub

		}
	}
}
