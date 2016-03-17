package org.kyrincloud.Spider.core.queue;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSONObject;

/**
 * 等待人工输入验证码的队列
 * {"Cookie":"xxx","credit_ticket":"xxx","checkcode":"","timestamp":"xxx","keyword":"","browser":"xxx"} 放入等待验证码人工输入队列  ->
 * @author kyrin
 *
 */
public class WaitInputCheckCodeQueue {
	
	private static Map<String,JSONObject> queue=new ConcurrentHashMap<String,JSONObject>();
	
	public static JSONObject put(String key,JSONObject obj){
		 return queue.put(key,obj);
	}
	
	public static JSONObject putIfAbsent(String key,JSONObject obj){
		return queue.putIfAbsent(key, obj);
	}
	
	public static void put(Map<String,JSONObject> map) throws InterruptedException{
		queue.putAll(map);
	}
	
	public static JSONObject remove(String key){
		return queue.remove(key);
	}
	
	public synchronized static Map<String,JSONObject> removeTopN(int num){
		AtomicInteger flag=new AtomicInteger(0);
		Map<String,JSONObject> result=new ConcurrentHashMap<String,JSONObject>();
		if(queue.isEmpty()){
			return result;
		}
		for(String key:queue.keySet()){
			result.put(key,queue.remove(key));
			if(flag.getAndIncrement()>=num){
				break;
			}
		}
		return result;
	}
	
	public static JSONObject get(String key){
		return queue.get(key);
	}
	
	public static Set<String> getKeys(){
		return queue.keySet();
	}
	
	public static void clear(){
		queue.clear();
	}
	
	public boolean isEmpty(){
		return queue.isEmpty();
	}
}
