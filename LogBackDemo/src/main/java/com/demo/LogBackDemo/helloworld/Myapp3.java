package com.demo.LogBackDemo.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.access.joran.JoranConfigurator;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.status.OnConsoleStatusListener;
import ch.qos.logback.core.status.StatusManager;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * 
 * @author zhangerqiang
 *
 */
public class Myapp3 {
	final static Logger logger = LoggerFactory.getLogger(Myapp3.class);
	public static void main(String[] args) {
		
		LoggerContext lc=(LoggerContext) LoggerFactory.getILoggerFactory();
		StatusManager statusManager = lc.getStatusManager();
		OnConsoleStatusListener onConsoleListener = new OnConsoleStatusListener();
		statusManager.add(onConsoleListener);
		try{
			JoranConfigurator conf = new JoranConfigurator();
			conf.setContext(lc);
			// Call context.reset() to clear any previous configuration, e.g. default 
		    // configuration. For multi-step configuration, omit calling context.reset().
			lc.reset();
			conf.doConfigure("logback_2.xml");
		}catch(JoranException e){
			// StatusPrinter will handle this
		}
		
		StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
		logger.info("Entering application.");
		
		logger.info("Exiting application.");
	}
}
