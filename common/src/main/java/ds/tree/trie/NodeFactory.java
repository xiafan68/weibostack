package ds.tree.trie;

import weibo.UncertainObject;

public interface NodeFactory<T extends UncertainObject> {
	public Node<T> createNode(int level, T value, Node<T> parent);
}
