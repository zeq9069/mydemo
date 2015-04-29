package com.demo.DesignPatterns.MediatorPattern;

public class User1 extends User{
	
	 public User1(Mediator mediator) {
		 super(mediator);
	}

	@Override
	public void work() {
		System.out.println("The user1 start working!");
	}

}
