package com.demo.DesignPatterns.StrategyPattern;
/**
 * ***************************************
 * 
 * 		策略模式
 * 
 * 	 策略模式定义了一系列算法，并将每个算法封装起来，
 * 使他们可以相互替换，且算法的变化不会影响到使用算法
 * 的客户。需要设计一个接口，为一系列实现类提供统一的
 * 方法，多个实现类实现该接口，设计一个抽象类（可有可无，
 * 属于辅助类），提供辅助函数
 *
 * ****************************************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月29日]
 *
 */
public class StraregyPattern {

	public static void main(String args[]){
		String exp="10+10";
		
		ICalculator ca=new Plus();
		int result=ca.calculate(exp);
		
		System.out.println(String.format("The result of %s is  %d ", exp,result));
		
	}
	
	
}
