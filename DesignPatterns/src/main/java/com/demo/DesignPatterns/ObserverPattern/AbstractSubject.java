package com.demo.DesignPatterns.ObserverPattern;

import java.util.Enumeration;
import java.util.Vector;

public abstract class AbstractSubject implements Subject{

	private Vector<Observer> list=new Vector<Observer>();
	
	public void add(Observer observer) {
		list.add(observer);
	}

	public void remove(Observer observer) {
		list.remove(observer);
	}

	public void notifyObservers() {
		Enumeration<Observer> enumerations=list.elements();
		while(enumerations.hasMoreElements()){
			enumerations.nextElement().update();
		}
	}
}
