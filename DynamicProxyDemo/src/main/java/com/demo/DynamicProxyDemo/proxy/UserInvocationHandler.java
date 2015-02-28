package com.demo.DynamicProxyDemo.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.demo.DynamicProxyDemo.Annocations.Auth;

/**
 * JDK动态代理
 * 用户服务代理类，调用处理类
 * Kyrin 2015年1月8日
 *
 */
public class UserInvocationHandler implements InvocationHandler {

	Object obj;

	public UserInvocationHandler(Object obj) {
		this.obj = obj;
	}

	public Object bind(Object o) {
		return Proxy.newProxyInstance(o.getClass().getClassLoader(), o.getClass().getInterfaces(), this);
	}

	//反射
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//获取注解
		Annotation annotation[] = method.getAnnotations();
		boolean flag = this.doBefore(annotation);
		if (!flag) {
			return null;
		}
		Object object = method.invoke(obj, args);
		this.doAfter(annotation);
		return object;
	}

	//方法调用前
	public boolean doBefore(Annotation annotation[]) {
		System.out.println("方法调用前执行…………权限过滤");
		for (Annotation an : annotation) {
			if (an != null && an instanceof Auth) {
				Auth au = (Auth) an;
				if (au.value()) {
					System.out.println("----有权限操作----");
					return true;
				}
			}
		}
		System.out.println("----无权限操作----");
		return false;
	}

	//方法调用后
	public void doAfter(Annotation annotation[]) {
		System.out.println("方法调用后…………日志记录");
	}
}
