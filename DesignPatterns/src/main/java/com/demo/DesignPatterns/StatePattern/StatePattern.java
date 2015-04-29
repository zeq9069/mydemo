package com.demo.DesignPatterns.StatePattern;
/**
 * ***********************
 * 
 *  状态模式
 *  
 *  核心思想就是：
 *  	当对象的状态改变时，同时改变其行为，很好理解！就拿QQ来说，
 *  有几种状态，在线、隐身、忙碌等，每个状态对应不同的操作，而且你的
 *  好友也能看到你的状态，所以，
 *  
 *  状态模式就两点：
 *  1、可以通过改变状态来获得不同的行为。2、你的好友能同时看到你的变化。
 *  
 *  
 *  根据这个特性，状态模式在日常开发中用的挺多的，尤其是做网站的时候，我们有时希望根据对象的某一属性，区别开他们的一些功能，比如说简单的权限控制等。
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月29日]
 *
 */
public class StatePattern {
	
	public static void main(String args[]){
		State state1=new State("在线");
		Context context1=new Context(state1);
		context1.method();
		
		State state2=new State("隐身");
		Context context2=new Context(state2);
		context2.method();
	}
	

}
