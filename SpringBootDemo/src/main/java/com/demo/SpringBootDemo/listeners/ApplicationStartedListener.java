package com.demo.SpringBootDemo.listeners;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
/**
 * 事件侦听
 * @author zhangerqiang
 *
 */
public class ApplicationStartedListener implements ApplicationListener<ApplicationStartedEvent>{
	 
	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		System.out.println(String.format("[ The Class %s has started ! ]",event.getSource().getClass().getName()));
	}

}
