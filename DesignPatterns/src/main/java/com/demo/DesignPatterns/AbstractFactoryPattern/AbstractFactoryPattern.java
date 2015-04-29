package com.demo.DesignPatterns.AbstractFactoryPattern;
/**
 * *********************************************************************************
 *   
 *   抽象工厂方法模式
 *   
 *   	工厂方法模式有一个问题就是，类的创建依赖工厂类，也就是说，
 *   如果想要拓展程序，必须对工厂类进行修改，这违背了闭包原则，所以，
 *   从设计角度考虑，有一定的问题，如何解决？就用到抽象工厂模式，创
 *   建多个工厂类，这样一旦需要增加新的功能，直接增加新的工厂类就可以了，
 *   不需要修改之前的代码。
 *
 * 例如：
 * 
 *  我们使用的黑种人和黄种人，分别对应一个相应的工厂类，所有工厂类都实现Provider这个接口，
 *  如果我们要实现白种人的话，不需要修改现有得代码，只需要新创建一个白种人工厂实现Provider的接口就行了
 *
 * ********************************************************************************
 * 
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月29日]
 *
 */
public class AbstractFactoryPattern {
	
	public static void main(String args[]){
		YelloFactory yello=new YelloFactory();
		BlackFactory black=new BlackFactory();
		
		YelloPerson yperson=yello.produce();
		BlackPerson bperson=black.produce();
			
		yperson.run();
		bperson.run();
		
	}

}
