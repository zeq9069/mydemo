package com.demo.SpringBootDemo.listeners;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
/**
 * 事件侦听
 * 
 * 
 * An ApplicationStartedEvent is sent at the start of a run, but before any processing except the registration of listeners and initializers.
 * An ApplicationEnvironmentPreparedEvent is sent when the Environment to be used in the context is known, but before the context is created.
 * An ApplicationPreparedEvent is sent just before the refresh is started, but after bean definitions have been loaded.
 * An ApplicationReadyEvent is sent after the refresh and any related callbacks have been processed to indicate the application is ready to service requests.
 * An ApplicationFailedEvent is sent if there is an exception on startup.
 * 
 * @author zhangerqiang
 *
 */
public class EventListener implements ApplicationListener<ApplicationEvent>{

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		Object obj=event.getSource();
		System.out.println(String.format("[ The Current object : %s ]", obj.getClass().getName()));
	}
}
