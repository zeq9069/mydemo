package com.demo.SpringBootTest1.aware;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
/**
 * 通过 实现ApplicationContextAware
 * 来获取自己的单例
 * @author lenovo
 *
 */
@Component
public class TestAware implements ApplicationContextAware{
	
	private static ApplicationContext applicationContext;
	
	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}
	
	//获取被注入的单例bean
	public  static TestAware getInstance(){
		return applicationContext.getBean(TestAware.class);
	}
	
	
	public String test(){
		return "TestAware is running !";
	}

}
