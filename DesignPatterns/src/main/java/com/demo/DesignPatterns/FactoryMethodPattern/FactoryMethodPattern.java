package com.demo.DesignPatterns.FactoryMethodPattern;
/**
 * ***********************
 * 
 * 工厂方法模式和静态工厂方法模式
 * 
 *  工厂方法模式和静态工厂模式唯一的区别在于static
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月28日]
 *
 */
public class FactoryMethodPattern {
	
	public static void main(String ap[]){
		//<1>
		Person man=PersonFactory.product("man");
		man.eat();
		
		//<2>
		Person woman=PersonFactory.createWoman();
		woman.eat();
		
	}
}
