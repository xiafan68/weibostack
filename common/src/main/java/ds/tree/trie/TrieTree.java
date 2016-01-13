package ds.tree.trie;

import java.util.ArrayList;
import java.util.List;

import weibo.UncertainObject;

/**
 * 采用邻接表的方式存储, 子节点采用散列表存储
 * 
 * @author xiafan
 *
 */
public class TrieTree<T extends UncertainObject> {
	// private Map<Node<T>, Set<Node<T>>> matrix;
	private Node<T> root = null;
	NodeFactory<T> nodeFactory;

	public TrieTree(NodeFactory<T> nodeFactory) {
		this.nodeFactory = nodeFactory;
	}

	public void add(T value) {
		root = nodeFactory.createNode(0, value, null);
	}

	public Node<T> getRoot() {
		return root;
	}

	public Node<T> search(ArrayList<T> path) {
		Node<T> curNode = root;
		Node<T> pNode = null;
		int i = 0;
		while (curNode != null && curNode.getValue().equals(path.get(i))) {
			pNode = curNode;
			curNode = curNode.getChild(path.get(++i));
		}

		if (curNode != null && curNode.getValue().equals(path.get(i)))
			return curNode;
		else
			return pNode;
	}

	public void add(Node<T> node, T value) {
		node.append(value);
	}

	public void addPath(List<T> path) {
		if (root == null) {
			root = nodeFactory.createNode(0, path.get(0), null);
		}
		Node<T> pNode = root;
		for (int i = 1; i < path.size(); i++) {
			Node<T> child = pNode.getChild(path.get(i));
			if (child == null) {
				child = nodeFactory.createNode(pNode.getLevel() + 1, path.get(i), pNode);
				pNode.append(child);
			} else {
				if (!child.getValue().certain() && path.get(i).certain()) {
					if (child.getParent() != null)
						child.getParent().updateNode(child, path.get(i));
				}
			}
			pNode = child;
		}
	}
}
