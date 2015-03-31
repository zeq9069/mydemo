package com.demo.NettyDemo.example.eight.factory;
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
public class ServerFactory extends AbstractServerFactory{

	private static  ServerFactory factory=null;
	public static ServerFactory getInstance(){
		if(factory==null){
			return new ServerFactory();
		}
		return factory;
	}
}
