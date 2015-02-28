package com.demo.Log4jDemo;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *@author kyrin
 */
public class Log4jFactory {
	
	private static Logger logger=Logger.getLogger(Log4jFactory.class);
	private static void init(){
		PropertyConfigurator.configure("src/main/java/log4j.properties");
	}
    public static void main( String[] args ){
    	init();
    	logger.info("This is a info message!");
    	logger.debug("This is a debug message!");
    	logger.error("This is a error message!");
    }
}
