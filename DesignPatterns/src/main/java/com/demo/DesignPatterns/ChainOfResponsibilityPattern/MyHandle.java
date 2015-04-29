package com.demo.DesignPatterns.ChainOfResponsibilityPattern;

public class MyHandle extends AbstractHandle implements Handle{

	String name;
	
	public MyHandle(String name){
		this.name=name;
	}
	
	public void operator() {
		System.out.println(String.format("The %s deal !", name));
		if(getHandle()!=null){
			getHandle().operator();
		}
	}
}
