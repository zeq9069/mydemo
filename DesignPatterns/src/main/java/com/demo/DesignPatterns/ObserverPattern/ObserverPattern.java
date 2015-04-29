package com.demo.DesignPatterns.ObserverPattern;
/**
 * ***********************
 * 
 *     观察者模式
 *     
 *     
 *    观察者模式很好理解，类似于邮件订阅和RSS订阅，当我们浏览一些博客或wiki时，
 *  经常会看到RSS图标，就这的意思是，当你订阅了该文章，如果后续有更新，会及时你。
 *  其实，简单来讲就一句话：当一个对象变化时，其它依赖该对象的对象都会收到通知，并
 *  且随着变化！对象之间是一种一对多的关系。
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月29日]
 *
 */
public class ObserverPattern {

	public static void main(String args[]){
		MySubject my=new MySubject();
		Observer1 ob1=new Observer1();
		Observer2 ob2=new Observer2();
		my.add(ob1);
		my.add(ob2);
		
		my.operation();
	}
}
