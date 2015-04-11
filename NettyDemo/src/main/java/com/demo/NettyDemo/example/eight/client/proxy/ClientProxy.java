package com.demo.NettyDemo.example.eight.client.proxy;

import java.lang.reflect.Proxy;

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
public class ClientProxy{
	public static Object getProxy(Class<?> c,String interfaceName){
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{c}, new ClientProxyHandler(interfaceName));
	}
}
