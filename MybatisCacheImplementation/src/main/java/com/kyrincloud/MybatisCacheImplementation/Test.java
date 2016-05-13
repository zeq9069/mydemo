package com.kyrincloud.MybatisCacheImplementation;

import java.util.concurrent.TimeUnit;

import com.kyrincloud.MybatisCacheImplementation.cache.decorators.FifoCache;
import com.kyrincloud.MybatisCacheImplementation.cache.decorators.LRUCache;
import com.kyrincloud.MybatisCacheImplementation.cache.decorators.ScheduledCache;
import com.kyrincloud.MybatisCacheImplementation.cache.impl.PerpetualCache;

/**
 * 
 * @author kyrin
 *
 */
public class Test {
	
	public static void main(String[] args) throws InterruptedException {
		FifoCache fifoCache=new FifoCache(new PerpetualCache());
		LRUCache lru=new LRUCache(new PerpetualCache());
		for(int i=0;i<1034;i++){
			fifoCache.put(i, "www"+i);
			lru.put(i, "www"+i);
		}
		System.out.println(fifoCache.getSize());
		System.out.println(lru.getSize());
		
		ScheduledCache sc=new ScheduledCache(new PerpetualCache());
		sc.setClearInterval(100);
		sc.put("1", "123");
		System.out.println(sc.getSize());
		TimeUnit.MILLISECONDS.sleep(100);
		System.out.println(sc.getSize());
	}

}
