package com.demo.DesignPatterns.ObserverPattern;

public interface Subject {
	
	/*
	 * 添加观察者
	 */
	public void add(Observer observer);
	
	/*
	 * 删除观察者
	 */
	public void remove(Observer observer);
	
	/*
	 *通知所有观察者 
	 */
	public void notifyObservers();

	/*
	 * 自身操作
	 */
	public void operation();
	
}
