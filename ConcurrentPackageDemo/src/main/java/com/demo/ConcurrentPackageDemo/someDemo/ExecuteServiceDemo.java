package com.demo.ConcurrentPackageDemo.someDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ***********************
 * 
 *  
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月26日]
 *
 */
public class ExecuteServiceDemo {

	public static void main(String args[]){
		ExecutorService execute=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		System.out.println("当前处理器内核数目："+Runtime.getRuntime().availableProcessors());
	
	}
}
