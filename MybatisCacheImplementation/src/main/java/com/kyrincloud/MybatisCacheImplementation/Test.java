package com.kyrincloud.MybatisCacheImplementation;

import com.kyrincloud.MybatisCacheImplementation.cache.decorators.FifoCache;
import com.kyrincloud.MybatisCacheImplementation.cache.decorators.LRUCache;
import com.kyrincloud.MybatisCacheImplementation.cache.impl.PerpetualCache;

/**
 * 
 * @author kyrin
 *
 */
public class Test {
	
	public static void main(String[] args) {
		FifoCache fifoCache=new FifoCache(new PerpetualCache());
		LRUCache lru=new LRUCache(new PerpetualCache());
		for(int i=0;i<1034;i++){
			fifoCache.put(i, "www"+i);
			lru.put(i, "www"+i);
		}
		System.out.println(fifoCache.getSize());
		System.out.println(lru.getSize());
	}

}
