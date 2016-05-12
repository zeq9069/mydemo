package com.kyrincloud.MybatisCacheImplementation.cache;

/***
 * cache interface
 * @author kyrin
 *
 */
public interface Cache {
	
	public String getId();
	
	public Object getObject(Object key);
	
	public void put(Object key,Object value);
	
	public Object removeObject(Object key);
	
	public void clear();
	
	public int getSize();
	
}
