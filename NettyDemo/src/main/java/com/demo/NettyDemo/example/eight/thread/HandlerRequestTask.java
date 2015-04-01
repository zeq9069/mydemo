package com.demo.NettyDemo.example.eight.thread;

import java.util.concurrent.Callable;
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
 * @date [2015年4月1日]
 *
 */
public class HandlerRequestTask implements Callable<Response>{

	private Request request;
	public HandlerRequestTask(Request request){
		this.request=request;
	}
	
	public Response call() throws Exception {
		Response response=new Response();
		response.setId(request.getId());
		response.setResult(request);
		return response;
	}
}
