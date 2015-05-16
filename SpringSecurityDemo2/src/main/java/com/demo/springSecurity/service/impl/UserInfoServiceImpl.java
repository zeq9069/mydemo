package com.demo.springSecurity.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.demo.springSecurity.dao.UserInfoDao;
import com.demo.springSecurity.domain.UserInfo;
import com.demo.springSecurity.service.UserService;

public class UserInfoServiceImpl implements UserService{

	private UserInfoDao userInfoDao;
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		UserInfo userInfo= userInfoDao.findUserInfoByName(username);
		if(userInfo==null){
			throw new UsernameNotFoundException("用户名"+username+"不存在");
		}
		return userInfo;
	}
	
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}
}
