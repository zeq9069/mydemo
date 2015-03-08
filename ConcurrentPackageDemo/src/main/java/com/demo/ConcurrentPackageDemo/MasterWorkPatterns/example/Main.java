package com.demo.ConcurrentPackageDemo.MasterWorkPatterns.example;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.demo.ConcurrentPackageDemo.MasterWorkPatterns.Master;

/**
 * **************************
 * master-work设计模式
 * 
 * example的主调用类
 * 
 * 
 * **************************
 * 
 * @author kyrin [2015年3月8日]
 *
 */
public class Main {
	
	static int re=0;
	
	public static void main(String args[]) throws Exception{
		long start=System.currentTimeMillis();
		
		Master master=new Master(new PlusWorker(), 3);
		for(int i=1;i<=100;i++){
			master.submit(i);
		}
		
		
		master.execute();
		
		ConcurrentHashMap<String, Object> resultMap=master.getResultMap();
		
		while(resultMap.size()>0 || !master.isComplete()){
			Set<String> keys=resultMap.keySet();
			String key=null; 
			for(String k:keys){
				key=k;
				break;
			}
			Integer i=null;
			
			if(key!=null)
				i=(Integer) resultMap.get(key);
			if(i!=null)
				re+=i.intValue();
			if(key!=null)
				resultMap.remove(key);
		}
		System.out.println("最终结果："+re);
		System.out.println("总耗时："+(System.currentTimeMillis()-start));
	}
}
