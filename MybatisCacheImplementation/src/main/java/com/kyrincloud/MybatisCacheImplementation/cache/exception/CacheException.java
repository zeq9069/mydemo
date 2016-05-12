package com.kyrincloud.MybatisCacheImplementation.cache.exception;


/**
 * cache Exception 
 * @author kyrin
 *
 */
public class CacheException extends RuntimeException{

	private static final long serialVersionUID = 5871667071906888409L;
	
	public CacheException() {
		super();
	}
	
	public CacheException(String msg){
		super(msg);
	}

}
