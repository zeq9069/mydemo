package com.demo.LogBackDemo.helloworld;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * 
 * @author zhangerqiang
 *
 */
public class MyApp2 {
	
	public static void main(String[] args) {
	    // assume SLF4J is bound to logback in the current environment
		LoggerContext lc=(LoggerContext) LoggerFactory.getILoggerFactory();
		
	    // print logback's internal status
		StatusPrinter.print(lc);
	}
}
