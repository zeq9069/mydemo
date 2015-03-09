package com.demo.ConcurrentPackageDemo.GuardedSuspensionPatterns;

/**
 * ***************************
 * 
 *   Guarded Suspension 
 *  
 *  客户端：负责对服务器发出请求
 * 
 * ***************************
 * 
 * @author kyrin [2015年3月9日]
 * 
 */
public class ClientThread extends Thread{
	private RequestQueue requestQueue;                //请求队列
	
	public ClientThread(RequestQueue requestQueue,String name){
		super(name);
		this.requestQueue=requestQueue;
	}

	@Override
	public void run() {
		for(int i=0;i<10;i++){                   	//构造请求
			Request request=new Request("RequestID:"+i+" Thread_name:"+Thread.currentThread().getName());
			System.out.println(Thread.currentThread().getName()+" requests "+request);
			requestQueue.addRequest(request);      //提交请求
			try {
				Thread.sleep(10);                  //客户端请求速度
												   //快于服务器处理的速度
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Client's name is "+Thread.currentThread().getName());
		}
	}
}
