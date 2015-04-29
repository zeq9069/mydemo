package com.demo.DesignPatterns.ObserverPattern;

public class MySubject extends AbstractSubject{

	public void operation() {
		System.out.println("The MySubject update self !");
		notifyObservers();
	}
}
