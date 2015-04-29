package com.demo.DesignPatterns.MementoPattern;
/**
 * ***********************
 *  
 *   备忘录模式
 *
 *		主要目的是保存一个对象的某个状态，以便在适当的时候恢复对象，
 *	个人觉得叫备份模式更形象些，通俗的讲下：假设有原始类A，A中有各种
 *	属性，A可以决定需要备份的属性，备忘录类B是用来存储A的一些内部状态，
 *	类C呢，就是一个用来存储备忘录的，且只能存储，不能修改等操作。
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月29日]
 *
 */
public class MementoPattern {
	
	public static void main(String a[]){
		Original or=new Original();
		or.setValue("Kyrin");
		Storage st=new Storage(or.createMemento());
		
		System.out.println(String.format("The value is %s", or.getValue()));
		
		or.setValue("Jack");
		
		System.out.println(String.format("The value is %s", or.getValue()));

		
		or.restoreMemento(st.getMemento());
		
		System.out.println(String.format("The value is %s", or.getValue()));

		
		
		
	}

}
