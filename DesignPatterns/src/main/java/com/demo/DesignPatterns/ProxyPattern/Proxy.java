package com.demo.DesignPatterns.ProxyPattern;

public class Proxy implements Sourceable{
	
	private Source source=null;
	public Proxy(){
		this.source=new Source();
	}
	public void method1() {
		before();
		source.method1();
		after();
	}
	
	private void before(){
		System.out.println("before do something");
	}
	
	private void after(){
		System.out.println("after do something");
	}
	

}
