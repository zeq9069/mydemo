package com.demo.LogBackDemo.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * logback 初探
 * 
 * @author zhangerqiang
 *
 */
public class HelloWorld2 {
	
	public static void main(String[] args) {
		Logger logger =LoggerFactory.getLogger(HelloWorld2.class);
		logger.debug("Hello world !");
		
		//打印logger状态
		LoggerContext lc=(LoggerContext)LoggerFactory.getILoggerFactory();
		StatusPrinter.print(lc);
	}

}
