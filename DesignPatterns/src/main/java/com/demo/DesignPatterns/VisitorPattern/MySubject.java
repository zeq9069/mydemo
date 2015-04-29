package com.demo.DesignPatterns.VisitorPattern;

public class MySubject implements Subject{

	public void accept(Visitor visitor) {
		visitor.visitor(this);
	}

	public String getSubject() {
		return "love";
	}

}
