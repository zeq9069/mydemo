package com.kyrincloud.MybatisCacheImplementation.cache.decorators;

import java.util.Deque;
import java.util.LinkedList;

import com.kyrincloud.MybatisCacheImplementation.Cache;

/**
 * FIFO cache decorator
 * @author kyrin
 *
 */
public class FifoCache implements Cache{

	private final Cache cache;
	
	private Deque<Object> keyList;
	
	private int size;
	
	public FifoCache(Cache cache){
		this.cache=cache;
		this.keyList=new LinkedList<Object>();
		this.size=1024;
	}
	
	
	public String getId() {
		return cache.getId();
	}

	public Object getObject(Object key) {
		return cache.getObject(key);
	}

	public void put(Object key, Object value) {
		cycleKeyList(key);
		cache.put(key, value);
	}

	public Object removeObject(Object key) {
		return cache.removeObject(key);
	}

	public void clear() {
		cache.clear();
		keyList.clear();
	}

	public int getSize() {
		return this.cache.getSize();
	}
	
	private void cycleKeyList(Object key){
		keyList.addLast(key);
		if(getSize()>=size){
			Object oldestKey=keyList.removeFirst();
			cache.removeObject(oldestKey);
		}
	}
}
