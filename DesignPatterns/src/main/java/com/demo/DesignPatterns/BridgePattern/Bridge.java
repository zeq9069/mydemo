package com.demo.DesignPatterns.BridgePattern;

public class Bridge extends AbstractBridge{

	@Override
	public void method1() {
		getSourceable().method1();
	}

}
