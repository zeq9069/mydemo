package com.demo.DesignPatterns.BridgePattern;

public abstract class AbstractBridge {
	
	private Sourceable sourceable;
	
	
	public void method1(){
		sourceable.method1();
	}
	
	public Sourceable getSourceable() {
		return sourceable;
	}

	public void setSourceable(Sourceable sourceable) {
		this.sourceable = sourceable;
	}
}
