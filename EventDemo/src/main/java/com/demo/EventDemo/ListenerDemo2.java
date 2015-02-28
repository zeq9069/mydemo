package com.demo.EventDemo;

public class ListenerDemo2 implements ListenerDemo {

	public void handlerEvent(EventDemo ed) {
		System.out.println("listenerDemo2 is run ");
		ed.say();//回调
	}

}
