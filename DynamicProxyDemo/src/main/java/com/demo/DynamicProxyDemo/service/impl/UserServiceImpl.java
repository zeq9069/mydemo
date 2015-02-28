package com.demo.DynamicProxyDemo.service.impl;

import com.demo.DynamicProxyDemo.service.UserService;

/**
 * 用户接口实现类
 * Kyrin 2015年1月8日 
 *
 */
public class UserServiceImpl implements UserService {

	public String getUserName(String id) {
		System.out.println("-----------getUserName执行了-----------");
		return "Kyrin";
	}

	public void deleteUser(String id) {
		System.out.println("-----------deleteUser执行了-----------");
	}

}
