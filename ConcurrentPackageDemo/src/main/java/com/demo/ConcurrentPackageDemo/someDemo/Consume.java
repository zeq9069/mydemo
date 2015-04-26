package com.demo.ConcurrentPackageDemo.someDemo;
import java.util.Queue;
/**
 * ***********************
 * 
 *  消费者
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月26日]
 *
 */
public class Consume implements Runnable{
	private Queue<String> queue=null;
	String name="";
	
	public Consume(Queue<String> queue,String name){
		this.queue=queue;
		this.name=name;
	}
	
	public void run() {
		synchronized (queue) {
			while(true){
				if(queue.size()==0){
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
				String res=queue.poll();
				queue.notifyAll();
				System.out.println(name+"消费者获取："+res);
			}
		}
	}
}