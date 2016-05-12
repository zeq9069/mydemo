package com.kyrincloud.MybatisCacheImplementation.cache.decorators;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kyrincloud.MybatisCacheImplementation.Cache;

/**
 * LRU cache decorator
 * 
 * @author kyrin
 *
 */
public class LRUCache implements Cache{

	private final Cache cache;
	private Map<Object,Object> keyMap;
	private Object eldestKey;
	
	public LRUCache(Cache cache){
		this.cache=cache;
		setSize(1024);
	}
	
	private void setSize(final int size){
		keyMap=new LinkedHashMap<Object, Object>(size,0.75f,true){
			
			private static final long serialVersionUID = 2216463996326133762L;
			
			@Override
			protected boolean removeEldestEntry(Map.Entry<Object, Object> eldest) {
				boolean tooBig = getSize() > size;
				if (tooBig) {
					eldestKey = eldest.getKey();
				}
				return tooBig;
			}
		};
	}
	
	public String getId() {
		return cache.getId();
	}

	public Object getObject(Object key) {
		keyMap.get(key);//touch
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
		keyMap.clear();
	}

	public int getSize() {
		return cache.getSize();
	}
	
	private void cycleKeyList(Object key){
		keyMap.put(key,key);
		if(eldestKey!=null){
			keyMap.remove(key);
			eldestKey=null;
		}
	}
}
