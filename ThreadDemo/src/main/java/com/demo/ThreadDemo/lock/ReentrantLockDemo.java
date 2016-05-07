package com.demo.ThreadDemo.lock;

import java.util.Stack;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁相关学习
 *  ReentrantLock
 * @author kyrin
 *
 */
public class ReentrantLockDemo {
	
	static ReentrantLock lock=new ReentrantLock();
	
	static Stack<String> stack=new Stack<String>();
	
	static Condition isNull=lock.newCondition();

	static Condition isFull=lock.newCondition();

	static boolean stopMe=false;
	
	static class Writer implements Runnable{

		public void run() {
			lock.lock();
			try{
				while(!stopMe){
					if(stack.size()==10){
						isFull.signal();
						try {
							isNull.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}else{
						for(int i=0;i<10;i++){
							stack.push(i+"");
						}
						try {
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}finally{
				lock.unlock();
			}
		}
	}
	
	static class Reader implements Runnable{
		public void run() {
			lock.lock();
			try{
				while(!stopMe){
					if(stack.size()==0){
							isNull.signal();
							isFull.await();
					}else{
						while(stack.size()>0){
							System.out.println(stack.pop());
							System.out.println("读");
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally{
				lock.unlock();
			}
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1=new Thread(new Writer(),"Writer");
		Thread t3=new Thread(new Reader(),"Reader");
		t3.start();
		t1.start();
		TimeUnit.SECONDS.sleep(10);
		stopMe=true;
		t3.join();
		t1.join();
	}
	
}
