package com.demo.akkaDemo.actor.example2;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

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
public class Actor1_test {
	
	public static void main(String args[]){
		//传递参数的时候，使用props
		Props p1=Props.create(Actor1.class,"参数1","参数2");
		// ActorSystem is a heavy object: create only one per application
		ActorSystem system=ActorSystem.create("mySystem");
		ActorRef actor1=system.actorOf(p1, "actor1");
		actor1.tell("Hello",actor1);
		
		system.shutdown();
	}

}
