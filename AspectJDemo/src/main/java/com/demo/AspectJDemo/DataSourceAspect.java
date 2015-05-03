package com.demo.AspectJDemo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.demo.AspectJDemo.annotation.ChangeFor;
import com.demo.AspectJDemo.datasource.DynamicDataSource;

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
	
	private static Logger logger=Logger.getLogger(DataSourceAspect.class);
	
	//对ChangeFor注解的拦截
	@Pointcut(value="@annotation(com.demo.AspectJDemo.annotation.ChangeFor)")
	public void inWebSevice(){
	}
	
	/*@Before(value="inWebSevice()")
	public void before(){
		System.out.println("Aspect before is running !");
	}
	
	//被拦截的方法的返回值
	@AfterReturning(value="inWebSevice()",returning="val")
	public void afterReturning(String val){
		System.out.println("Aspect afterReturn is runnning !  return "+val.toString());
	}
	
	@After(value="inWebSevice()")
	public void after(){
		System.out.println("Aspect after is runnning ! ");
	}
	
	//argNames 指定的值要跟被拦截的方法内的值保持一致
	@AfterReturning(value="inWebSevice()",argNames="id")
	public void afterReturn2(){
		System.out.println("Aspect afterReturning and argNames is runnning ! ");
	}
	
	@AfterThrowing(value="inWebSevice()",throwing="ex")
	public void afterThrowing(Exception ex){
		System.out.println("The target method return exception : "+ex.getMessage());
	}
	*/
	
	@Around(value="inWebSevice()")
	public void around(ProceedingJoinPoint pj) throws Throwable{
		logger.info("The Around has been start !");
		MethodSignature sig=(MethodSignature) pj.getSignature();
		//获取被拦截的方法
		Method method=sig.getMethod();
		Annotation annotation=method.getAnnotation(ChangeFor.class);
		if(annotation!=null){
			ChangeFor changeFor=(ChangeFor)annotation;
			DynamicDataSource.changeFor(changeFor.value());
			logger.info("成功切换到数据源："+changeFor.value());
		}
		pj.proceed();
		logger.info("The Around has been stop !");
	}
}
