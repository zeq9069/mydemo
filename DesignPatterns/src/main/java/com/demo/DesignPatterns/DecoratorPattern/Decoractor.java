package com.demo.DesignPatterns.DecoratorPattern;

public class Decoractor implements Sourceable{
	
	private Source source;
	
	public Decoractor(Source source){
		this.source=source;
	}

	public void method1() {
		System.out.println("before do something");
		source.method1();
		System.out.println("after do something");
	}

}
