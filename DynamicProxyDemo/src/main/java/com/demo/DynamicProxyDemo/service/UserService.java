package com.demo.DynamicProxyDemo.service;

import com.demo.DynamicProxyDemo.Annocations.Auth;

/**
 * 用户接口
 * Kyrin 2015年1月8日 
 *
 */
public interface UserService {
	@Auth(value = true)
	public String getUserName(String id);

	@Auth(value = false)
	public void deleteUser(String id);
}
