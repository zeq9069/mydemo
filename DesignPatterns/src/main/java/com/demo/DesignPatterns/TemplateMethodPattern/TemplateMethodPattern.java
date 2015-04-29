package com.demo.DesignPatterns.TemplateMethodPattern;
/**
 * *************************************************
 * 
 *  	模板方法模式
 *	一个抽象类中，有一个主方法，再定义1...n个方法，可以是抽象的，
 *也可以是实际的方法，定义一个类，继承该抽象类，重写抽象方法，通过
 *调用抽象类，实现对子类的调用.
 *
 * 说白了就是将具体的处理方法延迟到子类中去处理，抽象类中暂时不做处理
 * 
 * ****************************************************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月29日]
 *
 */
public class TemplateMethodPattern {
	
	public static void main(String s[]){
		String exp = "8+8";  
        AbstractCalculator cal = new Plus();  
        int result = cal.calculate(exp, "\\+");  
        System.out.println(String.format("The result of %s is %d", exp,result));  
       
        
        String exp1 = "8*8";  
        AbstractCalculator p = new Multiply();  
        int res = p.calculate(exp1, "\\*");  
        System.out.println(String.format("The result of %s is %d", exp1,res));  
        
		
	}

}
