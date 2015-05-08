package com.demo.AspectJDemo;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

import com.demo.AspectJDemo.annotation.ChangeFor;
import com.demo.AspectJDemo.annotation.DataSourceDistribute;
import com.demo.AspectJDemo.annotation.DataSourceEntity;
import com.demo.AspectJDemo.annotation.DataSourceGroup;
import com.demo.AspectJDemo.annotation.Group;
import com.demo.AspectJDemo.datasource.DynamicDataSource;
import com.demo.AspectJDemo.filter.MonitorFilter;

/**
 * ***********************
 *  
 *  aspectj 面向切面测试
 *
 *	为了让本注解的加载顺序大于@Transaction或者其他注解，
 *默认使用@Order来定义一个中等顺序，应定要比@Transaction的顺序要大
 *（否则在使用@Transaction的情况下，数据源会切换失败）
 *
 *也不能使用最大顺序，否则在使用@DataSourceDistribute时，
 *会报错，因为该出借是定义在类级别的
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年5月1日]
 *
 */
@Aspect
@Order(value=0)
public class DataSourceAspect extends AbstractDataSourceAspect{
	
	private static Logger logger=Logger.getLogger(DataSourceAspect.class);
	
	
	public DataSourceAspect(){
		this(null);
	}
	
	public DataSourceAspect(List<MonitorFilter> filters) {
		super(filters);
	}
	
	
	@Pointcut(value="@annotation(com.demo.AspectJDemo.annotation.ChangeFor)")
	public void inWebService(){}
	
	@Pointcut(value="@within(com.demo.AspectJDemo.annotation.DataSourceDistribute)")
	public void inWebServiceClass(){}
	
	@Pointcut(value="@within(com.demo.AspectJDemo.annotation.DataSourceGroup)")
	public void inWebServiceClass2(){}
	
	@Pointcut(value="inWebService() || inWebServiceClass() || inWebServiceClass2()")
	public void all(){}
	
	
	private  boolean dealDataSourceDistribute(DataSourceDistribute dataSourceDistribute,String methodName){
		DataSourceEntity [] entity=dataSourceDistribute.value();
		if(entity!=null){
			for(DataSourceEntity en:entity){
				if(Pattern.matches(en.methodPattern(), methodName)){
					DynamicDataSource.changeFor(en.dataSource());
					return true;
				}
			}
		}
		
		return false;
	}
	
	private  boolean dealDataSourceGroup(DataSourceGroup dataSourceGroup,String methodName){
		Group[] groups=dataSourceGroup.groups();
		if(groups!=null){
			for(Group group:groups){
				if(Pattern.matches(group.methodPattern(), methodName)){
					DynamicDataSource.getInstance().changeToByGroup(group.groupName());
					return true;
				}
			}
		}	
		
		return false;
	}
	
	
	private void  dealChangeTo(ChangeFor changeFor){
		DynamicDataSource.changeFor(changeFor.value());
		logger.info("成功切换到数据源："+changeFor.value());
	}
	
	//添加synchronized关键字，避免在同一个group下的数据源轮训不均匀
	@Around(value="all()")
	public  synchronized Object around(ProceedingJoinPoint pj) throws Throwable{
		logger.info("The Around has been start !");
		MethodSignature sig=(MethodSignature) pj.getSignature();
		Method method=sig.getMethod();
		String name=method.getName();
		ChangeFor changeFor=method.getAnnotation(ChangeFor.class);
		if(changeFor!=null){
			dealChangeTo(changeFor);
			return process(pj);
		}
		Object obj=pj.getTarget();
		DataSourceDistribute dataSourceDistribute=obj.getClass().getAnnotation(DataSourceDistribute.class);
		DataSourceGroup dataSourceGroup=obj.getClass().getAnnotation(DataSourceGroup.class);
		if(dataSourceDistribute!=null){
			if(dealDataSourceDistribute(dataSourceDistribute,name)){
				dealDataSourceDistribute(dataSourceDistribute, name);
				return process(pj);
			}
		}
		if(dataSourceGroup!=null){
			dealDataSourceGroup(dataSourceGroup,name);
		}
		return process(pj);
	}
	
	
	public Object process(ProceedingJoinPoint pj) throws Throwable{
		//Before do something
		super.before();
		
		//方法执行
		Object result=pj.proceed();
		
		//after do something
		super.after();
		
		return result;
	}
	
	

	/*@Before(value="all()")
	public  synchronized void classBefore(JoinPoint jp){
		super.before();
		MethodSignature sig=(MethodSignature) jp.getSignature();
		Method method=sig.getMethod();
		String name=method.getName();
		ChangeFor changeFor=method.getAnnotation(ChangeFor.class);
		if(changeFor!=null){
			dealChangeTo(changeFor);
			return;
		}
		Object obj=jp.getTarget();
		DataSourceDistribute dataSourceDistribute=obj.getClass().getAnnotation(DataSourceDistribute.class);
		DataSourceGroup dataSourceGroup=obj.getClass().getAnnotation(DataSourceGroup.class);
		if(dataSourceDistribute!=null){
			if(dealDataSourceDistribute(dataSourceDistribute,name)){
				return;
			}
			
		}
		if(dataSourceGroup!=null){
			dealDataSourceGroup(dataSourceGroup,name);
		}
	}
	
	@After(value="all()")
	public void after(){
		super.after();
	}*/
	
	

	
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

}