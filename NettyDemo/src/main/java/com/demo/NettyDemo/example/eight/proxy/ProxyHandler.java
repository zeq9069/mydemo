package com.demo.NettyDemo.example.eight.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


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
public class ProxyHandler implements InvocationHandler{
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		return null;
	}

}
