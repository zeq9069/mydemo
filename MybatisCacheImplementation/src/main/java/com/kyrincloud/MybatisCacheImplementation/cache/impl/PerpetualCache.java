package com.kyrincloud.MybatisCacheImplementation.cache.impl;

import java.util.HashMap;
import java.util.Map;

import com.kyrincloud.MybatisCacheImplementation.Cache;
import com.kyrincloud.MybatisCacheImplementation.cache.exception.CacheException;

/**
 * cache impl
 * @author kyrin
 *
 */
public class PerpetualCache implements Cache{

	private String id;
	
	private Map<Object,Object> cache=new HashMap<Object, Object>();
	
	public String getId() {
		return id;
	}

	public Object getObject(Object key) {
		return cache.get(key);
	}

	public void put(Object key, Object value) {
		cache.put(key, value);
	}

	public Object removeObject(Object key) {
		return cache.remove(key);
	}

	public void clear() {
		cache.clear();
	}

	public int getSize() {
		return cache.size();
	}
	
	@Override
	public boolean equals(Object obj){
		if(id==null || obj==null || !(obj instanceof Cache)){
			return false;
		}
		Cache o=(Cache)obj;
		if(o.getId()==null){
			throw new CacheException("The cache implementation require a ID.");
		}
		return id.equals(o.getId());
	}

	@Override
	public int hashCode() {
		if(id==null)
			throw new CacheException("The cache implementation require a ID.");
		return id.hashCode();
	}

}
