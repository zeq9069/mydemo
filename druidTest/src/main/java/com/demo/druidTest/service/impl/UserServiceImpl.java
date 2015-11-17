package com.demo.druidTest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.druidTest.domain.User;
import com.demo.druidTest.repository.UserDao;
import com.demo.druidTest.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao<User, Integer> userDaoImpl;
	
	@Transactional
	@Override
	public boolean save(User user) {
		return userDaoImpl.add(user)!=null?true:false;
	}

	@Transactional(readOnly=true)
	@Override
	public User get(int id) {
		return userDaoImpl.findOne(id);
	}
	
}
