package com.demo.ConcurrentPackageDemo.GuardedSuspensionPatterns;
/**
 * ****************************
 * 
 *   Guarded Suspension 设计模式
 * 
 * 模拟服务端进程
 * 
 * ****************************
 * @author kyrin [2015年3月9日]
 *
 */
public class ServerThread extends Thread{

	private RequestQueue requestQueue;                                //请求队列
	
	public ServerThread(RequestQueue requestQueue,String name) {
			super(name);
			this.requestQueue=requestQueue;
	}
	
	@Override
	public void run() {
		while(true){
			final Request request=requestQueue.getRequest();           //得到请求
			try {
				Thread.sleep(100);                                     //模拟请求耗时
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println(Thread.currentThread().getName()+" handles "+request);
		}
	}
}
