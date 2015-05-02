package com.demo.AspectJDemo;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * ***********************
 *  
 *  aspectj 面向切面测试
 *
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年5月1日]
 *
 */
@Component
@Aspect
public class DataSourceAspect {
	
	@Pointcut(value="@annotation(com.demo.AspectJDemo.annotation.ChangeFor)")
	public void pointcut(){
		System.out.println("This is a pointcut");
	}
	
	@Before(value="pointcut()")
	public void before(){
		System.out.println("Aspect before running !");
	}
	
	
	
}
