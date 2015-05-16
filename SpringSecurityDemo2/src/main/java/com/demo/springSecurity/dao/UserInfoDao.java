package com.demo.springSecurity.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.demo.springSecurity.domain.UserInfo;

public interface UserInfoDao {
	
	public  UserInfo findUserInfoByName(String username);
	
}
