package com.demo.SpringBootDemo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import com.demo.SpringBootDemo.listeners.ApplicationStartedListener;
import com.demo.SpringBootDemo.listeners.EventListener;


@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableGlobalMethodSecurity
public class SpringApplicationConfig {
	public static void main(String[] args) {
		SpringApplication sp=new SpringApplication(SpringApplicationConfig.class);
		//添加listener
		sp.addListeners(new EventListener());
		sp.addListeners(new ApplicationStartedListener());
		
		//使得命令行下输入的参数失效
		sp.setAddCommandLineProperties(false); 
		
		sp.run(args);
		//SpringApplication.run(SpringApplicationConfig.class, args);
		
	}
	
}
