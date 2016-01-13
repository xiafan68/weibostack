package ds.tree.trie;

import java.util.Iterator;
import java.util.Stack;

import util.Pair;
import weibo.UncertainObject;

public class PostOrderTraverse<T extends UncertainObject> implements Iterator<Node<T>> {
	Node<T> root;

	public PostOrderTraverse(TrieTree<T> tree) {
		this.root = tree.getRoot();
		stack.add(new Pair<Node<T>, Iterator<Node<T>>>(root, root.iterator()));
	}

	Stack<Pair<Node<T>, Iterator<Node<T>>>> stack = new Stack<Pair<Node<T>, Iterator<Node<T>>>>();

	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	@Override
	public Node<T> next() {
		Node<T> ret = null;
		while (!stack.isEmpty()) {
			Pair<Node<T>, Iterator<Node<T>>> cur = stack.peek();
			if (cur.getValue().hasNext()) {
				Node<T> child = cur.getValue().next();
				stack.push(new Pair<Node<T>, Iterator<Node<T>>>(child, child.iterator()));
			} else {
				ret = cur.getKey();
				stack.pop();
				break;
			}
		}
		return ret;
	}

	@Override
	public void remove() {
		throw new RuntimeException("not supported operation on PostOrderTraverse");
	}
}
