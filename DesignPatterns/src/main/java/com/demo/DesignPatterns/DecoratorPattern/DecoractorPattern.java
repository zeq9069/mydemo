package com.demo.DesignPatterns.DecoratorPattern;
/**
 * ***********************
 * 
 * 装饰着模式
 * 
 * 	顾名思义，装饰模式就是给一个对象增加一些新的功能，而且是动态的，
 * 要求装饰对象和被装饰对象实现同一个接口，装饰对象持有被装饰对象的实例
 *
 * （感觉这个和类适配器模式和代理模式有点类似）
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月29日]
 *
 */
public class DecoractorPattern {

	public static void main(String a[]){
		Decoractor d=new Decoractor(new Source());
		d.method1();
	}
	
}
