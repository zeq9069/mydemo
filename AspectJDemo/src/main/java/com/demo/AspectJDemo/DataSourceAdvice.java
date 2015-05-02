package com.demo.AspectJDemo;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

/**
 * ***********************
 * 
 *   spring aop
 *   
 *   实现数据源切换，这个与@aspect性质是一样的，两者是一个级别
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年5月2日]
 *
 */
@Component
public class DataSourceAdvice  implements MethodBeforeAdvice{

	@Override
	public void before(Method arg0, Object[] arg1, Object arg2)
			throws Throwable {
		System.out.println("Spring Aop is running !");
	}

}
