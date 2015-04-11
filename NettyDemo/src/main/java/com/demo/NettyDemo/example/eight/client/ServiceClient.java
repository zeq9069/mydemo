package com.demo.NettyDemo.example.eight.client;


import com.demo.NettyDemo.example.eight.client.factory.ClientFactory;
import com.demo.NettyDemo.example.eight.client.proxy.ClientProxy;
import com.demo.NettyDemo.example.eight.service.UserService;
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
public class ServiceClient {
	private static final String host="127.0.0.1";
	private static final int port=9090;
	public static void main(String a[]){
		ClientFactory factory=ClientFactory.getInstance();
		factory.connect(host, port);
		UserService userService=(UserService)ClientProxy.getProxy(UserService.class, "com.demo.NettyDemo.example.eight.service.UserService");
		for(int i=0;i<=10000;i++){
			System.out.println(i+"[远程服务开始调用,结果为：]----> "+userService.get(1).getId());
		}
		
		factory.stop();
	}
}
