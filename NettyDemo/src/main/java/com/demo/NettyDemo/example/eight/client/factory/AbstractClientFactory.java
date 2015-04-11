package com.demo.NettyDemo.example.eight.client.factory;

import io.netty.channel.ChannelFuture;

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
 * @date [2015年4月8日]
 *
 */
public abstract class AbstractClientFactory implements Client{
	
	protected static Map<Integer,Response> responseMap=new HashMap<Integer, Response>();

	public abstract void connect(String host,int port) ;
	
	public  abstract ChannelFuture createClient() throws Exception;
	
	public abstract void sendRequest(ChannelFuture future,Request request);
	
	public abstract void stop();
	
	public void receiveResponse(Response response){
		responseMap.put(response.getId(), response);
	}
	public void removeReponse(Response response){
		responseMap.remove(response).getId();
	}
}
