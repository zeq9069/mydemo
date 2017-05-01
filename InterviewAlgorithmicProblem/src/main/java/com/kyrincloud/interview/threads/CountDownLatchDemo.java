package com.kyrincloud.interview.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * 等待所有的线程执行完了一起执行下一组任务
 * 
 * @author zhangerqiang
 *
 */

public class CountDownLatchDemo {
	
	
	private ExecutorService exec = Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors());
	
	private List<Task> start = new ArrayList<Task>();
	
	private List<Task> middle = new ArrayList<Task>();

	private List<Task> end = new ArrayList<Task>();

	
	public CountDownLatchDemo(List<Task> start , List<Task> middle , List<Task> end) {
		this.start = start;
		this.middle = middle;
		this.end = end;
	}
	
	public void exec(){
		try {
			pre();
			middle();
			after();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void pre() throws InterruptedException{
		if(start == null){
			return;
		}
		final java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(start.size());
		for(final Task t : start){
			exec.submit(new Runnable() {
				
				public void run() {
					t.run();
					latch.countDown();
				}
			});
		}
		latch.await();
	}
	
	private void middle() throws InterruptedException{
		if(start == null){
			return;
		}
		final java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(start.size());
		for(final Task t : start){
			exec.submit(new Runnable() {
				
				public void run() {
					t.run();
					latch.countDown();
				}
			});
		}
		latch.await();
	}
	
	private void after() throws InterruptedException{
		if(start == null){
			return;
		}
		final java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(start.size());
		for(final Task t : start){
			exec.submit(new Runnable() {
				
				public void run() {
					t.run();
					latch.countDown();
				}
			});
		}
		latch.await();
	}
	
	
	abstract class Task {
		public abstract void run() ;
	}
	
	public static void main(String[] args) {
		CountDownLatchDemo demo = new CountDownLatchDemo(null, null, null);
		demo.exec();
	}

}
