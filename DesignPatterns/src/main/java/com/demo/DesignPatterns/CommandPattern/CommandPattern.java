package com.demo.DesignPatterns.CommandPattern;

/**
 * ********************************************************
 *   
 *   				命令模式
 *   
 *  	命令模式很好理解，举个例子，司令员下令让士兵去干件事情，
 *  从整个事情的角度来考虑，司令员的作用是，发出口令，口令经过传
 *  递，传到了士兵耳朵里，士兵去执行。这个过程好在，三者相互解耦，
 *  任何一方都不用去依赖其他人，只需要做好自己的事儿就行，司令员要
 *  的是结果，不会去关注到底士兵是怎么实现的
 *
 *
 *		这个很哈理解，命令模式的目的就是达到命令的发出者和执行者之间解耦，
 *	实现请求和执行分开，熟悉Struts的同学应该知道，Struts其实就是一种将
 *	请求和呈现分离的技术，其中必然涉及命令模式的思想！其实每个设计模式都是
 *	很重要的一种思想，看上去很熟，其实是因为我们在学到的东西中都有涉及，尽
 *	管有时我们并不知道，其实在Java本身的设计之中处处都有体现，像AWT、JDBC、
 *	集合类、IO管道或者是Web框架，里面设计模式无处不在
 *
 * ***********************************************************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月29日]
 *
 */
public class CommandPattern {
	
	
	public static void main(String args[]){
		Receiver r=new Receiver();
		Command command=new MyCommand(r);
		Invoker in=new Invoker(command);
		
		in.action();
		
	}

}
