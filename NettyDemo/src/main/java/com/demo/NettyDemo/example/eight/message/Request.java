package com.demo.NettyDemo.example.eight.message;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;
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
public class Request implements Serializable{
	
	private static final long serialVersionUID = -2924926999454830215L;
	private  final int id=getAtomic();
	private String interfaceName;
	private Method method;
	private Object[] args;
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
	public int getId() {
		return id;
	}
	private int getAtomic(){
		return new AtomicInteger(1).incrementAndGet();
	}
	
}
