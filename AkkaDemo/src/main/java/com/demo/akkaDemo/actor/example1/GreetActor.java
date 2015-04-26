package com.demo.akkaDemo.actor.example1;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * ***********************
 * 
 *  
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月23日]
 *
 */
public class GreetActor extends UntypedActor{
	
	
	
	@Override
	public void preStart() throws Exception {
		 final ActorRef hello =
	               getContext().actorOf(Props.create(HelloActor.class), "hello");
	       hello.tell("Hello", getSelf());
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof String){
			if(message.equals("hello")){
				getSender().tell("hello world!",getSelf());
			}else{
				unhandled(message);
			}
		}
	
		
	}

}
