package com.demo.ConcurrentPackageDemo.GuardedSuspensionPatterns;
/**
 * ********************************************
 * 
 *  主函数：负责启动Guarded Suspension设计模式模拟案例
 * 
 * ********************************************
 * @author kyrin [2015年3月9日]
 *
 */
public class Main {
	
	public static void main(String a[]){
		RequestQueue requestQueue=new RequestQueue();
		for(int i=0;i<10;i++){
			new ServerThread(requestQueue, "ServerThread_"+i).start();
		}
		
		for (int i = 0; i < 10; i++) {
			new ClientThread(requestQueue, "ClientThread_"+i).start();
		}
	}
}
