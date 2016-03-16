package org.kyrincloud.Spider.core.queue;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;

/**
 * 等待人工输入验证码的队列
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
