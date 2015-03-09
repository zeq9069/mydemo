package com.demo.ConcurrentPackageDemo.GuardedSuspensionPatterns;

import java.util.LinkedList;

/**
 ****************************
 * 
 *  Guarded Suspension 
 * 
 * 请求队列，存放请求实体
 *
 ****************************
 * @author kyrin [2015年3月9日]
 *
 */
public class RequestQueue {
	
	public LinkedList<Request> queue=new LinkedList<Request>(); 
	
	public synchronized Request getRequest(){
		
		while(queue.size()==0){
			try {
				wait();                              // 等待直到有新的请求request加入进来
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return queue.remove();                      //返回request队列中的第一个请求
	}

	public synchronized void addRequest(Request request) {
		queue.add(request);                        //添加新的请求
		notifyAll();							   //通知getRequest()方法
	}
	
	

}
