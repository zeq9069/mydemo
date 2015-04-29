package com.demo.DesignPatterns.IteratorPattern;

public interface Collection {
	
	public Iterator iterator();
	
	/*
	 * 获取聚合元素
	 */
	public Object get(int i);
	
	
	public int size();
	

}
