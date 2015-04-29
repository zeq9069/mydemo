package com.demo.DesignPatterns.VisitorPattern;

public class MyVisitor implements Visitor{

	public void visitor(Subject subject) {
		System.out.println(String.format("visitor the subject : %s ",subject.getSubject(),subject.getSubject()));
	}

}
