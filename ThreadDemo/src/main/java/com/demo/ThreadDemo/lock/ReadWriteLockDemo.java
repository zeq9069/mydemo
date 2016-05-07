package com.demo.ThreadDemo.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * 读写锁 ReentrantReadWriterLock
 * @author kyrin
 *
 */
public class ReadWriteLockDemo {

	
	
	
	
	public static void main(String[] args) throws InterruptedException {
		
		final MySafeMap<String, String> map=new MySafeMap<String, String>();//线程安全,当然加了锁之后，效率会降低很多
		//final Map<String, String> map=new HashMap<String, String>();//线程不安全，但是效率高
		
		System.out.println(System.currentTimeMillis());
		Thread t1=new Thread(new Runnable() {
			public void run() {
				for(int i=0;i<1000;i++){
					map.put(""+i, ""+i);
				}
			}
		});
		
		Thread t2=new Thread(new Runnable() {
			public void run() {
				for(int i=1000;i<2000;i++){
					map.put(""+i, ""+i);
				}
			}
		});
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		System.out.println("总共数量："+map.size());
		for(int i=0;i<map.size();i++){
			System.out.print(i+" ");
		}
		System.out.println();
		System.out.println(System.currentTimeMillis());
		
	}
	
	
	
	
	static class MySafeMap<K,V>{
		
		Map<K,V> map=new HashMap<K,V>();
		
		ReadWriteLock rw=new ReentrantReadWriteLock();

		public void put(K key,V value){
			rw.writeLock().lock();
			try{
				map.put(key, value);
			}finally{
				rw.writeLock().unlock();
			}
		}
		
		public V get(K key){
			rw.readLock().lock();
			try{
				return map.get(key);
			}finally{
				rw.readLock().unlock();
			}
		}
		
		public void clear(){
			rw.writeLock().lock();
			try{
				map.clear();
			}finally{
				rw.writeLock().unlock();
			}
		}
		
		public int size(){
			rw.readLock().lock();
			try{
				return map.size();
			}finally{
				rw.readLock().unlock();
			}
		}
		
	}
}
