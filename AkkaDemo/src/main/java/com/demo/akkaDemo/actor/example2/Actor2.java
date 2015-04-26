package com.demo.akkaDemo.actor.example2;

import scala.Option;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;
import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorContext;
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
public class Actor2 extends UntypedActor{
	LoggingAdapter logger=Logging.getLogger(getContext().system(), Actor2.class);
	
	@Override
	public void aroundPostRestart(Throwable reason) {
		logger.info("aroundPostRestart ");
		super.aroundPostRestart(reason);
	}

	@Override
	public void aroundPostStop() {
		logger.info("aroundPostStop ");
		super.aroundPostStop();
	}

	@Override
	public void aroundPreRestart(Throwable reason, Option<Object> message) {
		logger.info("aroundPreRestart ");
		super.aroundPreRestart(reason, message);
	}

	@Override
	public void aroundPreStart() {
		logger.info("aroundPreStart ");
		super.aroundPreStart();
	}

	@Override
	public void aroundReceive(PartialFunction<Object, BoxedUnit> receive,
			Object msg) {
		logger.info("aroundReceive ");
		super.aroundReceive(receive, msg);
	}

	@Override
	public void onReceive(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		logger.info("onreceive ");
		getContext().stop(getSelf());

	}

	@Override
	public void postRestart(Throwable reason) throws Exception {
		logger.info("postRestart ");

		super.postRestart(reason);
	}

	@Override
	public void postStop() throws Exception {
		// TODO Auto-generated method stub
		logger.info("postStop ");
		super.postStop();
	}

	@Override
	public void preRestart(Throwable reason, Option<Object> message)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("preRestart ");
		super.preRestart(reason, message);
	}

	@Override
	public void preStart() throws Exception {
		// TODO Auto-generated method stub
		logger.info("preStart ");
		super.preStart();
	}
}
