package com.demo.ConcurrentPackageDemo.MasterWorkPatterns;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ***************************
 *  Master-work设计模式
 * 
 * work线程：负责处理具体的业务逻辑
 * 
 * **************************
 * 
 * @author kyrin [2015年3月8日]
 *
 */
public class Work implements Runnable{
	protected Queue<Object> workQueue;
	protected ConcurrentHashMap<String,Object> resultMap;
	
	
	
	public Queue<Object> getWorkQueue() {
		return workQueue;
	}



	public void setWorkQueue(Queue<Object> workQueue) {
		this.workQueue = workQueue;
	}



	public ConcurrentHashMap<String, Object> getResultMap() {
		return resultMap;
	}



	public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	//实际的业务逻辑：交给继承的子类去写实际的业务
	public Object handle(Object input){
		
		return input;
	}

	public void run() {
		
		while(true){
			Object input=workQueue.poll();
			if(input==null) break;
			Object result=handle(input);
			resultMap.put(Integer.toString(result.hashCode()), result);
		}
	}
}
