package com.demo.DesignPatterns.AdapterPattern;

/**
 * ***********************
 * 
 *  有时候，一个接口定义太多，实现类并不想
 *  实现所有方法，这时候，就需要abstract类
 *  来作为中转，让目标类在实现abstract类，
 *  选择性的覆盖方法
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月29日]
 *
 */
public abstract class AbstractWrapper2 implements Sourceable{

	public void method1(){
		System.out.println("I'm from AbstractWrapper2 class method1");
	}
	
	public void method2(){
		System.out.println("I'm from AbstractWrapper2 class method2");
	}
	
}
