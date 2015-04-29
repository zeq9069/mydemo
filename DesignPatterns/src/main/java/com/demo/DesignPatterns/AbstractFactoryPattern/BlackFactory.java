package com.demo.DesignPatterns.AbstractFactoryPattern;
/**
 * ***********************
 * 
 *  黑种人制造工厂
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月29日]
 *
 */
public class BlackFactory implements Provider{

	public BlackPerson produce() {
		return new BlackPerson();
	}

}
