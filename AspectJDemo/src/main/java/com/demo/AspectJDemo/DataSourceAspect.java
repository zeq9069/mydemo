package com.demo.AspectJDemo;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
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
	//对ChangeFor注解的拦截
	@Pointcut(value="@annotation(com.demo.AspectJDemo.annotation.ChangeFor)")
	public void pointcut(){
	}
	
	@Before(value="pointcut()")
	public void before(){
		System.out.println("Aspect before is running !");
	}
	
	//被拦截的方法的返回值
	@AfterReturning(value="pointcut()",returning="val")
	public void afterReturning(String val){
		System.out.println("Aspect afterReturn is runnning !  return "+val.toString());
	}
	
	@After(value="pointcut()")
	public void after(){
		System.out.println("Aspect after is runnning ! ");
	}
	
	//argNames 指定的值要跟被拦截的方法内的值保持一致
	@AfterReturning(value="pointcut()",argNames="id")
	public void afterReturn2(){
		System.out.println("Aspect afterReturning and argNames is runnning ! ");
	}
	
	@AfterThrowing(value="pointcut()",throwing="ex")
	public void afterThrowing(Exception ex){
		System.out.println("The target method return exception : "+ex.getMessage());
	}
	
	
	
}
