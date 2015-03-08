package com.demo.ConcurrentPackageDemo.FuturePatterns;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * **************************
 * 
 * Future 并发设计模式
 * 
 * 利用JDK自身带的Future来实现
 * 
 * **************************
 * 
 * @author kyrin [2015年3月8日]
 *
 */
public class FuturePatterns {
    public static void main( String[] args ) throws InterruptedException, ExecutionException{
    	FutureTask<String> future=new FutureTask<String>(new Process("Baby "));
    	ExecutorService executor=Executors.newFixedThreadPool(1);
    	executor.execute(future);
    	
    	//在future返回值之前，可以做一些耗时的其他任务
    	for(int i=0;i<=10;i++){
    		System.out.println("正在执行其他任务……");
    		Thread.sleep(200);
    		System.out.println("其他任务执行完毕……");
    	}
    	
    	System.out.println("其他任务已经执行完毕！现在获取关键任务执行返回的结果："+future.get());
    	
    }
}
