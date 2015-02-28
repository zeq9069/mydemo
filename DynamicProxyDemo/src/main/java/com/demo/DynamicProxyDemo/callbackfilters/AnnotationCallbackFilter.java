package com.demo.DynamicProxyDemo.callbackfilters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.CallbackFilter;

import com.demo.DynamicProxyDemo.Annocations.Auth;

/**
 * callbackFilter注解过滤
 * callbackFilter可以实现方法的过滤，但是没办法阻止方法的执行，不如methodInterceptor
 * @author kyrin
 *
 */
public class AnnotationCallbackFilter implements CallbackFilter {

	public int accept(Method method) {
		Annotation anno[] = method.getAnnotations();
		for (Annotation an : anno) {
			if (an instanceof Auth) {
				Auth auth = (Auth) an;
				if (auth.value()) {
					System.out.println("callbackFilter注解过滤---------------有权限");
				}

			}
		}
		return 0;
	}
}
