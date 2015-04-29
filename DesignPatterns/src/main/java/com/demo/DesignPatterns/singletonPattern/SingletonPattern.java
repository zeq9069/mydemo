package com.demo.DesignPatterns.singletonPattern;
/**
 * ***********************
 * 
 *  单例模式
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月28日]
 *
 */
public class SingletonPattern {
	
	/*
	 * 
	 * 实际情况是，单例模式使用内部类来维护单例的实现，JVM内部的机制能够保证当一个类被加载的时候，这个类的加载过程是线程互斥的
	 * 
	 * 这样就能保证当我们第一次调用getInstance()的时候，singletonPattern只会被创建一次
	 * 
	 * 还有一些其他的创建方式，比如synchronized，其实我们只要保证在创建对象的时候保证同步就可以了，获取的时候如果也加synchronized的话，这样效率不是很好
	 * 
	 * 构造函数设置为private 以防被创建
	 */
	
	private static SingletonPattern single=null;
	
	private SingletonPattern(){
	}
	
	private static class Instance{
		private static SingletonPattern singletonPattern=new SingletonPattern();
	}
	//<1>
	public static SingletonPattern getInstance(){
		return Instance.singletonPattern;
	}
	
	//<2>在创建实例的时候，加上synchronized，保证同步，因为实例值创建一次，所以效率上也没什么大的影响
	private static synchronized void create(){
		if(single==null){
			single=new SingletonPattern();
		}
	}
	
	public static SingletonPattern getInstance2(){
		if(single==null){
			create();
		}
		return single;
	}
	
	
}
