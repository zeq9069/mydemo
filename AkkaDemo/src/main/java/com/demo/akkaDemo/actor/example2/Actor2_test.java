package com.demo.akkaDemo.actor.example2;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Actor2_test {
	
	public static void main(String a[]){
		ActorSystem system=ActorSystem.create("mySystem");
		ActorRef ref=system.actorOf(Props.create(Actor2.class), "actor2");
		ref.tell("message", ref);
		system.shutdown();
	}

}
