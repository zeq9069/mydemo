package com.demo.DesignPatterns.BridgePattern;
/**
 * ************************************************************************
 * 
 * 		桥接模式
 * 
 * 
 * 	桥接模式就是把事物和其具体实现分开，使他们可以各自独立的变化。桥接的用意是：
 * 
 * 		将抽象化与实现化解耦，使得二者可以独立变化，像我们常用的JDBC桥DriverManager一样，
 * JDBC进行连接数据库的时候，在各个数据库之间进行切换，基本不需要动太多的代码，甚至丝毫不用动，
 * 原因就是JDBC提供统一接口，每个数据库提供各自的实现，用一个叫做数据库驱动的程序来桥接就行了
 *
 * ***********************************************************************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月29日]
 *
 */
public class BridgePattern {

	public static void main(String args[]){
		Bridge bridge=new Bridge();
		
		Source1 source1=new Source1();
		bridge.setSourceable(source1);
		bridge.method1();
		
		
		
		Source2 source2=new Source2();
		bridge.setSourceable(source2);
		bridge.method1();
		
		
		
	}
	
}
