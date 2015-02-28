package com.demo.akkaDemo.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class DemoProps {

	public void popTest() {
		//1,创建props的方式
		Props props1 = Props.empty();
		Props props2 = Props.create(DemoActor.class, null, null);
		Props props3 = props1.withDispatcher("dispatcher-id");
		Props props4 = props1.withDeploy(null);

		//2，使用props创建ACTOR
		ActorSystem system = ActorSystem.create("MySystem");
		ActorRef myactor1 = system.actorOf(new Props(null, DemoActor.class, null).withDispatcher("my-dispatcher"),
				"myactor1");
		ActorRef myactor2 = system.actorOf(new Props(null, DemoActor.class, null), "myactor2");

	}
}
