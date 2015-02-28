package com.demo.DynamicProxyDemo.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.demo.DynamicProxyDemo.Annocations.Auth;
import com.demo.DynamicProxyDemo.callbackfilters.AnnotationCallbackFilter;
import com.demo.DynamicProxyDemo.callbackfilters.MethodCallbackFilter;

/**
 * cglib动态代理实例
 * Kyrin 2015年1月8日
 *
 */
public class UserMethodIntercepter implements MethodInterceptor {

	public Object target;

	public Object getInstance(Object target) {
		this.target = target;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(this.target.getClass());
		enhancer.setCallback(this);
		enhancer.setCallbackFilter(new MethodCallbackFilter());//过滤
		enhancer.setCallbackFilter(new AnnotationCallbackFilter());
		return enhancer.create();
	}

	public Object intercept(Object obj, Method method, Object[] arg2, MethodProxy proxy) throws Throwable {
		Annotation annotation[] = method.getAnnotations();
		boolean flag = doBefore(annotation);
		if (!flag) {
			return null;
		}
		proxy.invokeSuper(obj, arg2);
		doAfter();
		return null;
	}

	public boolean doBefore(Annotation annotation[]) {
		System.out.println("方法执行前…………权限过滤");
		for (Annotation an : annotation) {
			if (an != null && an instanceof Auth) {
				Auth auth = (Auth) an;
				if (auth.value() == true) {
					System.out.println("-----有权限-----");
					return true;
				}
			}
		}
		System.out.println("-----无权限-----");
		return false;
	}

	public void doAfter() {
		System.out.println("方法执行之后…………日志记录");
	}
}
