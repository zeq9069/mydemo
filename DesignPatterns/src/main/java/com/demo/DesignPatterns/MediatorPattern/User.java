package com.demo.DesignPatterns.MediatorPattern;

public abstract class User {
	
	private Mediator mediator;
	
	public User(Mediator mediator){
		this.mediator=mediator;
	}
	
	public Mediator getMediator(){
		return mediator;
	}
	
	public abstract void work();

}
