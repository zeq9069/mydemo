package com.demo.DynamicProxyDemo.service.impl;

import com.demo.DynamicProxyDemo.Annocations.Auth;

/**
 * 与UserServiceImpl的唯一区别在于 userServiceImpl1没有实现接口
 * 这是为了区分jdk和cglib的动态代理
 * Kyrin 2015年1月8日
 *
 */
public class UserServiceImpl1 {
	@Auth(value = true)
	public String getUserName(String id) {
		System.out.println("-----------getUserName执行了-----------");
		return "Kyrin";
	}

	@Auth(value = false)
	public void deleteUser(String id) {
		System.out.println("-----------deleteUser执行了-----------");
	}
}
