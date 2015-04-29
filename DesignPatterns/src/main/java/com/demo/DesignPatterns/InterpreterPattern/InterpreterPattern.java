package com.demo.DesignPatterns.InterpreterPattern;
/**
 * ***********************
 * 
 *  解释模型
 *  
 *  一般主要应用在OOP开发中的编译器的开发中，所以适用面比较窄。
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月29日]
 *
 */
public class InterpreterPattern {
	public static void main(String a[]){
		
		//计算 10+20-8
		
		int res=new Minus().interpreter(new Context(new Plus().interpreter(new Context(10, 20)),8));
		
		System.out.println(String.format("The result is %d",res));
		
	}
}
