package com.kyrincloud.RPCDemo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.kyrincloud.RPCDemo.RPCClient;
import com.kyrincloud.RPCDemo.codec.MyCodec;

public class ClientProxy implements InvocationHandler{

	Class<?> clazz;
	
	public  Object newInstance(Class<?> clazz){
		this.clazz=clazz;
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{clazz}, this);
	}
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Class<?>[] _argsType=method.getParameterTypes();
		String[] argsType=new String[_argsType.length];//直接获取参数类型的name，在服务端，通过构造methodKey获取指定已经缓存的Method，这样可以避免实时的通过反射获取method时，泛型的影响
		byte[][] argsObj=new byte[_argsType.length][];
		for(int i=0;i<_argsType.length;i++){
			argsType[i]=_argsType[i].getName();
			argsObj[i]=MyCodec.encode(args[i]);
		}
		return RPCClient.invokeResult(clazz.getName(), method.getName(), argsType, argsObj);
	}
	
}
