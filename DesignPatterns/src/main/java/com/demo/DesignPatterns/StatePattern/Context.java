package com.demo.DesignPatterns.StatePattern;

public class Context {
	
	private State state;
	
	public Context(State state){
		this.state=state;
	}
	
	public void method(){
		if(state.getValue().equals("在线")){
			state.maethod1();
		}else if(state.getValue().equals("隐身")){
			state.method2();
		}
	}

}
