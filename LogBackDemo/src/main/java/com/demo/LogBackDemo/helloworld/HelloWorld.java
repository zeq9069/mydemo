package com.demo.LogBackDemo.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * logback 初探
 * 
 * @author zhangerqiang
 *
 */
public class HelloWorld {

	public static void main(String[] args) {
		
		Logger logger=LoggerFactory.getLogger(HelloWorld.class);
		logger.debug("Hello World !");
	}

}
