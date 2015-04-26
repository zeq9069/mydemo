package com.demo.akkaDemo.actor.example2;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * ***********************
 * 
 *  actor效率测试
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月25日]
 *
 */
public class Actor4_test {
	static ActorSystem system=ActorSystem.create("mySystem");
	public static void main(String a[]){
		for(int i=0;i<1000;i++){
			ActorRef ref=system.actorOf(Props.create(Actor4.class),"actor4"+i);
			ref.tell("Hello"+i, ActorRef.noSender());
		}
		
	}
}
