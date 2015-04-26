package com.demo.akkaDemo.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class DemoProps {

	public void popTest() {
		//1,创建props的方式
		Props props1 = Props.empty();
		Props props2 = Props.create(DemoActor.class,"参数","参数","参数");
		Props props3 = props1.withDispatcher("dispatcher-id");
		Props props4 = props1.withDeploy(null);

		//2，使用props创建ACTOR
		ActorSystem system = ActorSystem.create("MySystem");
		ActorRef myactor1 = system.actorOf(Props.create(DemoActor.class).withDispatcher("my-dispatcher"),
				"myactor1");
		ActorRef myactor2 = system.actorOf(Props.create(DemoActor.class), "myactor2");

	}
	
	public static void main(String args[]){
		ActorSystem system = ActorSystem.create("MySystem");
		ActorRef myactor2 = system.actorOf(Props.create(DemoActor.class), "myactor2");
		myactor2.tell("Hello",myactor2);
		
	}
	
}
