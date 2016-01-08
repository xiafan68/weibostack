package collection;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.Factory;

public class DefaultedPutMap<Key, Value> implements Map<Key, Value> {
	Map<Key, Value> map;
	Factory factory;

	public DefaultedPutMap(Map<Key, Value> map, Factory factory) {
		this.map = map;
		this.factory = factory;
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean containsKey(Object arg0) {
		return map.containsKey(arg0);
	}

	@Override
	public boolean containsValue(Object arg0) {
		return map.containsValue(arg0);
	}

	@Override
	public Set<java.util.Map.Entry<Key, Value>> entrySet() {
		return map.entrySet();
	}

	@Override
	public Value get(Object key) {
		Value ret = null;
		if (map.containsKey(key)) {
			ret = map.get(key);
		} else {
			ret = (Value) factory.create();
			map.put((Key) key, ret);
		}
		return ret;
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set<Key> keySet() {
		return map.keySet();
	}

	@Override
	public Value put(Key arg0, Value arg1) {
		return map.put(arg0, arg1);
	}

	@Override
	public void putAll(Map<? extends Key, ? extends Value> arg0) {
		map.putAll(arg0);
	}

	@Override
	public Value remove(Object arg0) {
		return map.remove(arg0);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<Value> values() {
		return map.values();
	}

	public static <Key, Value> DefaultedPutMap<Key, Value> decorate(
			Map<Key, Value> map, Factory factory) {
		return new DefaultedPutMap<Key, Value>(map, factory);
	}

}
