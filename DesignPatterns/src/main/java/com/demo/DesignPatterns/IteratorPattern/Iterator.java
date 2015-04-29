package com.demo.DesignPatterns.IteratorPattern;

public interface Iterator {
	
	/**
	 * 获取第一个元素
	 * @return
	 */
	public Object first();
	
	/*
	 * 下一个元素
	 */
	public Object next();
	
	public boolean hasNext();
	
	/*
	 * 前一个元素
	 */
	public Object previous();

}
