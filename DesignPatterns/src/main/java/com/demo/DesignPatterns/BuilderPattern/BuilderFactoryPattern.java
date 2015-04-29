package com.demo.DesignPatterns.BuilderPattern;
/**
 * ***********************
 * 
 *  创造者模式
 *
 * 	与工厂模式的区别就是：工厂模式关注的是创建单个产品，
 * 而建造者模式则关注创建符合对象，多个部分。因此，是选
 * 择工厂模式还是建造者模式，依实际情况而定。
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月29日]
 *
 */
public class BuilderFactoryPattern {
	
	public static void main(String a[]){
		Builder build=new Builder();
		build.buildBlackPerson(10);
		build.buildYelloPerson(10);
	}

}
