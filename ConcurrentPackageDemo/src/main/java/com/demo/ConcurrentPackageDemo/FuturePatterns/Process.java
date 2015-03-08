package com.demo.ConcurrentPackageDemo.FuturePatterns;

import java.util.concurrent.Callable;

/**
 * 
 * *************************
 * 
 * 模拟真实的业务处理类
 * 
 * *************************
 * 
 * @author kyrin [2015年3月8日]
 *
 */
public class Process implements Callable<String>{
	
	private String param;

	public Process(String param) {
		this.param=param;
	}

	public String call() throws Exception {
		System.out.println("［关键任务开始执行］……");
		for(int i=0;i<=10;i++){
			//模拟耗时逻辑
			Thread.sleep(100);
			param+=param;
		}
		System.out.println("［关键任务执行完毕］……");
		return param;
	}
}
