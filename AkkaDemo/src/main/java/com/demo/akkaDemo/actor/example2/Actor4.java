package com.demo.akkaDemo.actor.example2;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * ***********************
 * 
 *  效率测试
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月25日]
 *
 */
public class Actor4 extends UntypedActor{
	
	LoggingAdapter logger=Logging.getLogger(getContext().system(), Actor4.class);

	@Override
	public void onReceive(Object message) throws Exception {
		Thread.sleep(2000);
		logger.info("receive a message :"+message);
		//getContext().stop(getSelf());
	}

}


