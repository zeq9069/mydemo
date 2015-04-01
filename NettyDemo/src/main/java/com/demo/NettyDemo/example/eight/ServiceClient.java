package com.demo.NettyDemo.example.eight;

import com.demo.NettyDemo.example.eight.factory.ClientFactory;
import com.demo.NettyDemo.example.eight.message.Request;

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
public class ServiceClient {
	private static final String host="127.0.0.1";
	private static final int port=9090;
	public static void main(String a[]){
		ClientFactory factory=ClientFactory.getInstance();
		factory.connect(host, port);
		for(int i=0;i<10;i++){
			Request request=new Request();
			request.setArgs(null);
			request.setInterfaceName(null);
			request.setMethod(null);
			factory.sendRequest(request);
		}
		//factory.stop();
	}
}
