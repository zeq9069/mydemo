package com.demo.akkaDemo.actor.example1;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * ***********************
 * 
 *   helloActor 模拟actor之间的简单通信
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月23日]
 *
 */
public class HelloActor extends UntypedActor{
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	@Override
	public void preStart() throws Exception {
		 final ActorRef greeter =
	               getContext().actorOf(Props.create(GreetActor.class), "greeter");
	       greeter.tell("Hello", getSelf());
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof String){
			log.info(" helloActor recieve a message {}",message);
			getSender().tell("HelloActor-->hello", getSender());
		}else{
			unhandled(message);
		}
	}

}
