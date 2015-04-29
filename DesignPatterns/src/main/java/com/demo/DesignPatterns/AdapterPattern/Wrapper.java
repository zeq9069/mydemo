package com.demo.DesignPatterns.AdapterPattern;
/**
 * ***********************
 * 
 *  对象适配器
 *  
 *  其实是对对象的封装
 *  
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月29日]
 *
 */
public class Wrapper implements Targetable{

	private Source source;
	
	public Wrapper(Source source){
		this.source=source;
	}
	
	public void method1() {
		source.method1();
	}

	public void method2() {
		System.out.println("I'm from Targetable class method2");
	}

}
