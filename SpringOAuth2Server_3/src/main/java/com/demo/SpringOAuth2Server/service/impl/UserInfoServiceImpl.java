package com.demo.SpringOAuth2Server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.SpringOAuth2Server.dao.UserInfoDao;
import com.demo.SpringOAuth2Server.domain.UserInfo;
import com.demo.SpringOAuth2Server.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService{

	@Autowired
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