package com.test.Alldemo.LRU;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * LRU最近最少使用算法
 * @author kyrin
 *d
 */
public class LRUCache <K,V>{

	private  LinkedHashMap<K, V> map;
	private float loadFactor=0.8f;//cache中保存80%的数据量，算是最大负载,超过了80%的话，就删除最近最少使用的数据
	private int maxCacheSize;
	
	public LRUCache(int maxCacheSize) {
		this.maxCacheSize=maxCacheSize;
		int capacitySize=(int)Math.ceil(maxCacheSize/loadFactor)+1;
		
		map=new LinkedHashMap<K, V>(capacitySize, loadFactor, true){
			private static final long serialVersionUID = 6368887369036324051L;
			@Override
			protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
				return size()>LRUCache.this.maxCacheSize;
			}
		};
	}
	public V get(K key){
		return map.get(key);
	}
	
	public void put(K key,V value){
		map.put(key, value);
	}
	
	public int size(){
		if(map.isEmpty()){
			return 0;
		}	
		return map.size();
	}
	
	public Collection<Map.Entry<K, V>> all(){
		return new ArrayList<Map.Entry<K,V>>(map.entrySet());
	}

	
	
	public static void main(String[] args) {
		LRUCache<String, String> cache=new LRUCache<String, String>(7);
		for(int i=0;i<6;i++){
			cache.put(i+"", i+"");
		}
		String kk1="";
		for(Entry<String, String> m:cache.all()){
			kk1+=" "+m.getValue();
		}
		System.out.println("最后排序结果为："+kk1);
		
		System.out.println("命中第1个："+cache.get("1"));
		System.out.println("命中第5个："+cache.get("1"));
		System.out.println("命中第5个："+cache.get("0"));
		System.out.println("命中第4个："+cache.get("0"));

		cache.put("6","6");
		cache.put("7","7");
		cache.put("8","8");
		cache.put("9", "9");
		cache.put("10", "10");
		
		
		String kk="";
		for(Map.Entry<String, String> m:cache.all()){
			kk+=" "+m.getValue();
		}
		System.out.println("访问次数依次升高："+kk);
	}
}
