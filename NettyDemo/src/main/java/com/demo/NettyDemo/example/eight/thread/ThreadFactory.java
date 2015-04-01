package com.demo.NettyDemo.example.eight.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * ********************************
 *    netty 练习
 *    
 *   RPC：利用JDK和cglib实现远程服务调用
 *   编码：java序列化编码
 *
 * ********************************
 * @author kyrin kyrincloud@qq.com 
 * 
 * @date [2015年4月1日]
 *
 */
public class ThreadFactory {
	
	private  static final int THREAD_NUM=Runtime.getRuntime().availableProcessors();//CPU内核数
	private static ExecutorService executor=Executors.newFixedThreadPool(THREAD_NUM*2);
	private static class SingletonHolder {
		static final ThreadFactory instance = new ThreadFactory();
	}
	public static ThreadFactory getInstance(){
		return SingletonHolder.instance;
	}
	public ExecutorService getExecutor(){
		return executor;
	}
}
