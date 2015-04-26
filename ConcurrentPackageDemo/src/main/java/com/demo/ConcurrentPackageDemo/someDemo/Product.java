package com.demo.ConcurrentPackageDemo.someDemo;

import java.util.Queue;
/**
 * ***********************
 * 
 *  生产者
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月26日]
 *
 */
public class Product implements Runnable{
	private Queue<String> queue=null;
	private String name="";
	public Product(Queue<String> queue,String name){
		this.queue=queue;
		this.name=name;
	}
	
	public void run() {
		synchronized (queue) {
			while(true){
				if(queue.size()>5){
					try {
						queue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				queue.add("element_result");
				queue.notifyAll();
				System.out.println(name+"生产者添加元素");
			}
		}
	}
}