package com.demo.akkaDemo.actor.example2;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * ***********************
 * 
 *
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月25日]
 *
 */
public class Actor1 extends UntypedActor{
	LoggingAdapter logger=Logging.getLogger(getContext().system(), Actor1.class);

	private String arg1,arg2;
	public  Actor1(String arg1,String arg2) {
		this.arg1=arg1;
		this.arg2=arg2;
	}
	
	

	@Override
	public void onReceive(Object message) throws Exception {
		logger.info("Actor1 receive the arg is {} and {}",this.arg1,this.arg2);
		logger.info("Actor1 receive the message is {}",message);
		Props p=Props.create(Actor1.class,"ww","ee");
		ActorRef ref=getContext().actorOf(p, "childActor");
		ref.tell("I'm a childActor1", getSelf());
	}

}
