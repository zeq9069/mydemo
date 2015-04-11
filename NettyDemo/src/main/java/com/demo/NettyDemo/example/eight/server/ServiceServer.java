package com.demo.NettyDemo.example.eight.server;

import com.demo.NettyDemo.example.eight.server.factory.ServerFactory;

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
public class ServiceServer {
	
	public static void main(String a[]){
		ServerFactory server=ServerFactory.getInstance();
		server.start(9090);
	}
}
