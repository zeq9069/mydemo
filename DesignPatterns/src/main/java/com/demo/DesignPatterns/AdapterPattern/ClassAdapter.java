package com.demo.DesignPatterns.AdapterPattern;
/**
 * ***********************
 * 
 * 类适配器
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月29日]
 *
 */
public class ClassAdapter extends Source implements Targetable{

	public void method2() {
		System.out.println("I'm from Targetable class method2");
	}
}