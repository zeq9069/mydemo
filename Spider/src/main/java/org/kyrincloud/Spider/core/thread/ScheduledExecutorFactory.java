package org.kyrincloud.Spider.core.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/*
 * 
 */
public class ScheduledExecutorFactory {
	
	private static ScheduledExecutorService sch=Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
	
	@SuppressWarnings({ "rawtypes" })
	public static ScheduledFuture exe(Runnable command, long initialDelay,long delay,TimeUnit unit){
		return sch.scheduleWithFixedDelay(command, initialDelay, delay, unit);
	}
	
}
