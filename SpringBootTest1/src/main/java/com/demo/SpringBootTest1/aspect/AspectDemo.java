package com.demo.SpringBootTest1.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
/**
 * aop 拦截 
 * @author lenovo
 *
 */
@Component
@Aspect
public class AspectDemo {
	
	@Pointcut(value="execution(* com.demo.SpringBootTest1.service.*.*(..))")
	public void inWebService(){}
	
	
	
	@Before(value="inWebService()")
	public void before(JoinPoint jp){
		MethodSignature method=(MethodSignature) jp.getSignature();
		String methodName=method.getName();
		System.out.println(String.format("[ Before of The method %s running ]", methodName));	
	}
	
	@After(value="inWebService()")
	public void after(JoinPoint jp){
		MethodSignature method=(MethodSignature) jp.getSignature();
		String methodName=method.getName();
		System.out.println(String.format("[ After of The method %s running ]", methodName));	
	}
	
	
	
	
	
}
