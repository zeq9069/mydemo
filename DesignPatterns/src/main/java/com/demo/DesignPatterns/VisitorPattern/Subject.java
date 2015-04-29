package com.demo.DesignPatterns.VisitorPattern;

public interface Subject {
	
	public void accept(Visitor visitor);
	
	public String getSubject();

}
