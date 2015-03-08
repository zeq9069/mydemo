package com.demo.ConcurrentPackageDemo.MasterWorkPatterns;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 
 * **********************************
 * 
 *      Master-work设计模式
 * 
 * master：负责协调work线程和合并处理结果集
 * 
 * *********************************
 * 
 * @author kyrin [2015年3月8日]
 *
 */
public class Master {
	
	protected Queue<Object> workQueue=new ConcurrentLinkedQueue<Object>();
	protected HashMap<String, Thread> threadMap=new HashMap<String, Thread>();
	protected ConcurrentHashMap<String,Object> resultMap=new ConcurrentHashMap<String,Object>();
	
	public boolean isComplete(){
		for(Entry<String,Thread> entry:threadMap.entrySet()){
			if(entry.getValue().getState()!=Thread.State.TERMINATED){
				return false;
			}
		}
		return true;
	}
	
	public Master(Work worker,int workCount){
		worker.setWorkQueue(workQueue);
		worker.setResultMap(resultMap);
		for(int i=0;i<workCount;i++){
			threadMap.put(Integer.toString(i), new Thread(worker,Integer.toString(i)));
		}
	}
	
	//提交一个任务
	public void submit(Object obj){
		workQueue.add(obj);
	}
	
	
	public void execute(){
		for(Entry<String, Thread> entry:threadMap.entrySet()){
			entry.getValue().start();
		}
	}

	public Queue<Object> getWorkQueue() {
		return workQueue;
	}

	public void setWorkQueue(Queue<Object> workQueue) {
		this.workQueue = workQueue;
	}

	public HashMap<String, Thread> getThreadMap() {
		return threadMap;
	}

	public void setThreadMap(HashMap<String, Thread> threadMap) {
		this.threadMap = threadMap;
	}

	public ConcurrentHashMap<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
	
	
	
}
