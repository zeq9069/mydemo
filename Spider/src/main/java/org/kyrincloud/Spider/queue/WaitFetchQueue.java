package org.kyrincloud.Spider.queue;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;

/**
 * 等待使用的队列
 * @author kyrin
 *
 */
public class WaitFetchQueue {
	
	private static BlockingQueue<JSONObject> queue=new LinkedBlockingQueue<JSONObject>();
	
	public static boolean add(JSONObject obj){
		return queue.add(obj);
	}
	public static boolean add(Collection<JSONObject> objs){
		return queue.addAll(objs);
	}
	public static void put(JSONObject obj) throws InterruptedException{
		queue.put(obj);
	}
	public static boolean remove(JSONObject obj){
		return queue.remove(obj);
	}
	public static JSONObject poll(){
		return queue.poll();
	}
	public static JSONObject peek(){
		return queue.peek();
	}
	public static JSONObject poll(long timeout,TimeUnit unit) throws InterruptedException{
		return queue.poll(timeout, unit);
	}
	public static void clear(){
		queue.clear();
	}
	public static boolean isEmpty(){
		return queue.isEmpty();
	}
	
}
