package com.demo.NettyDemo.example.seven.javacode;

import java.io.Serializable;
import java.util.Date;

/**
 * *************************
 *   netty 练习
 *   
 *   测试实体， java序列化编解码
 *
 *
 * ************************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年3月30日]
 *
 */
public class UserInfo implements Serializable{
	
	private static final long serialVersionUID = 7633183694752094308L;
	private String name="kyrin(云中鹤)";
	private String password="123";
	private Date date=new Date();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
