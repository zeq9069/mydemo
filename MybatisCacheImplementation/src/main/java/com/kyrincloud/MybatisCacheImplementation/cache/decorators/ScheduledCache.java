package com.kyrincloud.MybatisCacheImplementation.cache.decorators;

import com.kyrincloud.MybatisCacheImplementation.Cache;

/**
 * scheduled cache decorators
 * @author kyrin
 *
 */
public class ScheduledCache implements Cache{

	 private Cache cache;
	 protected long clearInterval;
	 protected long lastClear;
	
	 public ScheduledCache(Cache cache) {
		 this.cache=cache;
		 this.clearInterval=60*60*1000;//1 hour
		 this.lastClear=System.currentTimeMillis();
	}
	 
	public long getClearInterval() {
		return clearInterval;
	}

	public void setClearInterval(long clearInterval) {
		this.clearInterval = clearInterval;
	}
	 
	public String getId() {
		return cache.getId();
	}

	public Object getObject(Object key) {
		return clearWhenStale()?null:cache.getObject(key);
	}

	public void put(Object key, Object value) {
		clearWhenStale();
		cache.put(key, value);
	}

	public Object removeObject(Object key) {
		clearWhenStale();
		return cache.removeObject(key);
	}

	public void clear() {
		cache.clear();
		lastClear=System.currentTimeMillis();
	}

	public int getSize() {
		clearWhenStale();
		return cache.getSize();
	}

	private boolean clearWhenStale(){
		if(System.currentTimeMillis()-lastClear>clearInterval){
			clear();
			return true;
		}
		return false;
	}
	
}
