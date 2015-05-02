package com.demo.AspectJDemo;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.demo.AspectJDemo.annotation.ChangeFor;

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
	
	/*@Before(value="pointcut()")
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
	*/
	
	@Around(value="pointcut()")
	public void around(ProceedingJoinPoint pj) throws Throwable{
		System.out.println("The Around has been start !");
		MethodSignature sig=(MethodSignature) pj.getSignature();
		//获取被拦截的方法
		Method method=sig.getMethod();
		
		//获取被拦截方法的参数
		Object[] args= pj.getArgs();
		ChangeFor changeFor=method.getAnnotation(ChangeFor.class);
		System.out.println("切换到数据源："+changeFor.value());
		pj.proceed();
		System.out.println("The Around has been stop !");
	}
	
	
}
