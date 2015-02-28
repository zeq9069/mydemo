package com.demo.DynamicProxyDemo.callbackfilters;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.CallbackFilter;

/**
 * cglib中的方法过滤
 * @author kyrin
 *
 */
public class MethodCallbackFilter implements CallbackFilter {

	public int accept(Method method) {
		if (method.getName().equals("getUserName")) {
			System.out.println("callbackFilter方法过滤----kyrin");
		}
		return 0;
	}
}
