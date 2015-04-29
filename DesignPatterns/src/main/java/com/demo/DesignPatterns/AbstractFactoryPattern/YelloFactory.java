package com.demo.DesignPatterns.AbstractFactoryPattern;
/**
 * ***********************
 * 
 *  黄种人生产工厂
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月29日]
 *
 */
public class YelloFactory implements Provider{

	public YelloPerson produce() {
		return new YelloPerson();
	}
}
