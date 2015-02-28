package com.demo.EventDemo;

import java.util.EventObject;

public class EventDemo extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EventDemo(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	public void say() {
		System.out.println("this is a method!");
	}

}
