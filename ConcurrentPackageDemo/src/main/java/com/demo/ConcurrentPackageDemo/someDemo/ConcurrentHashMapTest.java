package com.demo.ConcurrentPackageDemo.someDemo;

import java.util.HashMap;
import java.util.UUID;


/**
 * ************************************
 * 
 *    concurrentHashMap与hashMap的效率比较
 *
 *
 * *************************************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月12日]
 *
 */
public class ConcurrentHashMapTest {

	public void concurrentHashMapTest() throws InterruptedException{
		final HashMap<String,String> map=new HashMap<String, String>();
		Thread t=new Thread(new Runnable() {
			
			public void run() {
				for(int i=0;i<10000;i++){
					new Thread(new Runnable() {
						
						public void run() {
							map.put(UUID.randomUUID().toString(), "");
						}
					},"ftf"+i).start();
				}
				
			}
		},"ftf");
		
		t.start();
		t.join();
		
	}
	
	
	public static void main(String s[]){
		try {
			new ConcurrentHashMapTest().concurrentHashMapTest();
		} catch (InterruptedException e) {
			e.printStackTrace();
		};
		
	}
	
	
}
