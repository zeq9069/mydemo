package com.demo.SpringOAuth2Server.dao;

import com.demo.SpringOAuth2Server.domain.UserInfo;


public interface UserInfoDao {
	
	public  UserInfo findUserInfoByName(String username);
	
}