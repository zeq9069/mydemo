package com.kyrincloud.MybatisCacheImplementation.cache.decorators;

import com.kyrincloud.MybatisCacheImplementation.Cache;

/**
 * synchronized cache
 * @author kyrin
 *
 */
public class SynchronizedCache implements Cache{

	private Cache cache;
	
	public String getId() {
		return cache.getId();
	}

	public synchronized Object getObject(Object key) {
		return cache.getObject(key);
	}

	public synchronized void put(Object key, Object value) {
		cache.put(key, value);
	}

	public synchronized Object removeObject(Object key) {
		return cache.removeObject(key);
	}

	public synchronized void clear() {
		cache.clear();
	}

	public synchronized int getSize() {
		return cache.getSize();
	}

}
