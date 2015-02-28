package com.demo.akkaDemo.actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * 通过继承UntypedActor乱来自定义actor
 * @author kyrin
 *
 */
public class DemoActor extends UntypedActor {
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	@Override
	public void onReceive(Object message) throws Exception {
		// TODO Auto-generated method stub
		if (message instanceof String) {
			log.info("Received String message : {}", message);
		} else {
			unhandled(message);
		}
	}
}
