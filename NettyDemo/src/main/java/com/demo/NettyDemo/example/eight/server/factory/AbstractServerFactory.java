package com.demo.NettyDemo.example.eight.server.factory;

import java.util.HashMap;
import java.util.Map;

import com.demo.NettyDemo.example.eight.message.Request;
import com.demo.NettyDemo.example.eight.message.Response;

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
 * @date [2015年3月31日]
 *
 */
public abstract class AbstractServerFactory implements Server{

	private static Map<Integer,Request> requestMap=new HashMap<Integer, Request>();
	public abstract void start(int port) ;
	public abstract void stop();
	public abstract void sendResponse(Response response);
	
	public void receiveRequest(Request request){
		requestMap.put(request.getId(), request);
	}
	public Request getRequest(int id){
		return requestMap.remove(id);
	}
	public void removeRequest(Request request){
		requestMap.remove(request);
	}
	public boolean isEmpty(){
		return requestMap.isEmpty();
	}
	
}
