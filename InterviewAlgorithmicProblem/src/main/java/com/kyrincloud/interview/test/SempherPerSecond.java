package com.kyrincloud.interview.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SempherPerSecond {

	private static Semaphore semaphore = new Semaphore(10);
	
	static ExecutorService pool = Executors.newFixedThreadPool(100);

	public static void main(String[] args) {
		
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
			
			public void run() {
				for(int i = 0 ; i < 10 ; i++){
					semaphore.release(1);								//每秒10个线程启动，为了平滑些，就每隔100毫秒释放一个信号量
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
					}
				}
			}
		}, 1000, 500, TimeUnit.MILLISECONDS);
		
		
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
			
			public void run() {
				for(int i = 0; i < 5;i++){
					pool.submit(new Runnable() {
						
						public void run() {
							semaphore.acquireUninterruptibly();
							System.out.println("远程调用");
						}
					});
				}
			}
		}, 500, 500, TimeUnit.MILLISECONDS);
	}
}
