package com.demo.akkaDemo.actor.example2;


import java.util.concurrent.TimeUnit;

import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.Terminated;

/**
 * ******************************************
 * 
 *  信箱:用于与actor信息的传递和对actor生命周期的监控
 *
 * *****************************************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月25日]
 *
 */
public class Inbox_test {
	static ActorSystem system=ActorSystem.create("mySystem");
	static ActorRef target=system.actorOf(Props.create(Actor3.class), "actor3");
	//inbox获取actor返回的消息
	public void test1(){
		Inbox inbox=Inbox.create(system);
		inbox.send(target, "hello");
		Object obj=inbox.receive(Duration.create(1, TimeUnit.SECONDS));
		System.out.println("--"+obj);
		
		Object obj1=inbox.receive(Duration.create(1, TimeUnit.SECONDS));
		System.out.println("--"+obj1);
	}
	
	//inbox监控actor的终止
	public static void test2(){
		final Inbox inbox = Inbox.create(system);
		inbox.watch(target);
		target.tell(PoisonPill.getInstance(), ActorRef.noSender());
		if(inbox.receive(Duration.create(1, TimeUnit.SECONDS)) instanceof Terminated){
			System.out.println("actor 终止了--");
		}
	}

	public static void main(String a[]){
		test2();
	}
	
	
}
