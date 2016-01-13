package util;

import java.util.Map.Entry;

public class Pair<K, V> implements Entry<K, V> {
	K key;
	V value;

	public Pair() {
	}

	public Pair(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	public void setKey(K key) {
		this.key = key;
	}

	@Override
	public V setValue(V value) {
		V pre = this.value;
		this.value = value;
		return pre;
	}

	@Override
	public int hashCode() {
		return key.hashCode() + value.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Pair) {
			return key.equals(((Pair<K, V>) other).key) && value.equals(((Pair<K, V>) other).value);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Pair [key=" + key + ", value=" + value + "]";
	}
}
