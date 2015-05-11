package com.demo.SpringBootTest1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.SpringBootTest1.dao.UserDao;
import com.demo.SpringBootTest1.domain.User;
import com.demo.SpringBootTest1.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public void create(User user) {
		userDao.create(user);
	}

	@Override
	public User get(int id) {
		return userDao.get(id);
	}

}
