package com.demo.DesignPatterns.StatePattern;

public class State {
	
	private String value;
	
	public State(String value){
		this.value=value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	public void maethod1(){
		System.out.println(String.format("The state is %s,and State class method1 running",value));
	}
	
	public void method2(){
		System.out.println(String.format("The state is %s,and State class method2 running",value));
	}

}
