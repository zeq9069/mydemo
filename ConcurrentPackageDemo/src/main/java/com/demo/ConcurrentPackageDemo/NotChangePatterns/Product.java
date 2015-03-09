package com.demo.ConcurrentPackageDemo.NotChangePatterns;
/**
 *****************************
 *   
 *   不变模式
 *   
 * 一个不变的对象有4个特点：
 * 
 * <1>去除setter方法以及所有修饰自身属性的方法
 * <2>将所有属性设置为私有，并用final最标记，保证其不可修该
 * <3>确保没有子类可以修改修改其属性
 * <4>有一个可以创建完整对象的构造函数
 *
 *****************************
 * @author kyrin [2015年3月9日]
 *
 */
public final class Product {
	private final String name;
	private final String price;
	private final String content;
	
	public Product(String name,String price,String content) {
		super();
		this.name=name;
		this.price=price;
		this.content=content;
	}
	public String getName() {
		return name;
	}
	public String getPrice() {
		return price;
	}
	public String getContent() {
		return content;
	}
	
	

}
