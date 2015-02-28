package com.demo.EventDemo;

public class Test {

	public static void main(String a[]) {
		SourceDemo d = new SourceDemo();
		d.addListener(new ListenerDemo2());
		d.addListener(new ListenerDemo() {
			public void handlerEvent(EventDemo ed) {
				System.out.println("listenerDemo 匿名类  is run");
			}
		});
		d.notifyEvent();
	}
}
