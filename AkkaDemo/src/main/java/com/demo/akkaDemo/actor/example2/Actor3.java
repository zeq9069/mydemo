package com.demo.akkaDemo.actor.example2;


import scala.Option;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * ***********************
 * 
 *  actor 的生命周期
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月25日]
 *
 */
public class Actor3 extends UntypedActor{
	LoggingAdapter logger=Logging.getLogger(getContext().system(), Actor2.class);
	
	



	@Override
	public void onReceive(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		logger.info("onreceive message="+arg0.toString());
		getSender().tell("wwwwww", ActorRef.noSender());
		getSender().tell(new String("ggggg"), ActorRef.noSender());
		getContext().stop(getSelf());
	}

	
}
