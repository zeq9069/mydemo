package com.demo.NettyDemo.example.eight.client.proxy;

import io.netty.channel.ChannelFuture;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.demo.NettyDemo.example.eight.client.factory.ClientFactory;
import com.demo.NettyDemo.example.eight.message.Request;
import com.demo.NettyDemo.example.eight.service.User;

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
public abstract class AbstractProxyHandler implements InvocationHandler{
	
	private String interfaceName;
	
	public AbstractProxyHandler(String interfaceName){
		this.interfaceName=interfaceName;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Request request=new Request();
		request.setArgs(args);
		request.setInterfaceName(interfaceName);
		ClientFactory f=ClientFactory.getInstance();
		ChannelFuture future=f.createClient();
		f.sendRequest(future,request);
		System.out.println("[客户端代理执行]");
		User u=new User();
		u.setId(1);
		return u;
	}

}
