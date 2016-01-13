package ds.tree.trie;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import weibo.UncertainObject;

public class Node<T extends UncertainObject> {
	protected int level;
	protected T value;
	protected Node<T> parent;
	protected Map<T, Node<T>> children;

	public Node(int level, T value, Node<T> parent) {
		super();
		this.level = level;
		this.value = value;
		this.parent = parent;
		children = new HashMap<T, Node<T>>();
	}

	Node<T> getChild(T cValue) {
		return children.get(cValue);
	}

	public int degree() {
		return children.size();
	}

	public boolean isLeaf() {
		return children.isEmpty();
	}

	public Iterator<Node<T>> iterator() {
		// return new NodeIterator<T>(this, children.values());
		return children.values().iterator();
	}

	public void updateNode(Node<T> node, T newValue) {
		children.remove(node.getValue());
		node.getValue().reconcile(newValue);
		children.put(newValue, node);
	}

	/**
	 * 
	 * @param parent
	 * @param child
	 */
	public void delete(Node<T> child) {
		assert child != null;
		children.remove(child.getValue());
	}

	public Node<T> append(T cValue) {
		Node<T> child = new Node<T>(level + 1, cValue, this);
		children.put(cValue, child);
		return child;
	}

	public void append(Node<T> child) {
		children.put(child.getValue(), child);
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @return the parent
	 */
	public Node<T> getParent() {
		return parent;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Node<T> parent) {
		this.parent = parent;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Node [level=" + level + ", value=" + value + "]";
	}
}
