package com.demo.AspectJDemo.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.demo.AspectJDemo.service.UserService;

public class TestDemo1{
	
	private static ApplicationContext application;

	public static void main(String args[]){
		application = new ClassPathXmlApplicationContext("spring.xml");
		UserService userService=(UserService) application.getBean("userService");
		
		//userService.create();
		//userService.create();
		//userService.create();
		userService.delete("1");
	}
}
