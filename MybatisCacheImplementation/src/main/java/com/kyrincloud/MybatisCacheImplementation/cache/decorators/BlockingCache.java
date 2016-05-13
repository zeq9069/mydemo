package com.kyrincloud.MybatisCacheImplementation.cache.decorators;

import java.util.concurrent.ConcurrentHashMap;

import com.kyrincloud.MybatisCacheImplementation.Cache;

/**
 * blocking cache decorator
 * @author kyrin
 *
 */


public class BlockingCache implements Cache{
	
	private Cache cache;
	private ConcurrentHashMap<Object, Object> locks;
	
	public BlockingCache(Cache cache ) {
		this.cache=cache;
		this.locks=new ConcurrentHashMap<Object, Object>();
	}

	public String getId() {
		return cache.getId();
	}

	public Object getObject(Object key) {
		try{
			
		}finally{
			
		}
		return null;
	}

	public void put(Object key, Object value) {
		// TODO Auto-generated method stub
		
	}

	public Object removeObject(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	public void clear() {
		// TODO Auto-generated method stub
		
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
