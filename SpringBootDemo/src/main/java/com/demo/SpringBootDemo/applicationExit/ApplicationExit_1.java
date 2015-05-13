package com.demo.SpringBootDemo.applicationExit;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;
/**
 * 
 * 方法注解@PreDestroy  实现应用推出前通知
 * 
 * 
 * 
 * @author zhangerqiang
 *
 */
@Component
public class ApplicationExit_1 {
	
	//应用销毁
	@PreDestroy
	public void destroy(){
		System.out.println("[ I will exit ! GoodBye ! from ]"+this.getClass().getName());
	}

}
