package com.demo.DesignPatterns.MediatorPattern;

public class User2 extends User{
	
	 public User2(Mediator mediator) {
		 super(mediator);
	}

	@Override
	public void work() {
		System.out.println("The user2 start working!");
	}

}
