package com.demo.DesignPatterns.FlyweightPattern;

import java.sql.Connection;

/**
 * *************************************************
 *  
 *  	享元模式
 *  
 *	享元模式的主要目的是实现对象的共享，即共享池，当系统中对象多的时候可以减少
 *内存的开销，通常与工厂模式一起使用。
 *
 *	一提到共享池，我们很容易联想到Java里面的JDBC连接池，想想每个连接的特点，我
 *们不难总结出：适用于作共享的一些个对象，他们有一些共有的属性，就拿数据库连接池来
 *说，url、driverClassName、username、password及dbname，这些属性对于每个
 *连接来说都是一样的，所以就适合用享元模式来处理，建一个工厂类，将上述类似属性作为内
 *部数据，其它的作为外部数据，在方法调用时，当做参数传进来，这样就节省了空间，减少了
 *实例的数量。
 *
 * 本案例就是一个数据库连接池的例子
 * 
 * **************************************************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月29日]
 *
 */
public class FlyweightPattern {
	
	
	public static void main(String args[]){
		ConnectionPool pool=ConnectionPool.getInstance();
		Connection con=pool.getConnection();
		pool.release();
	}
	

}
