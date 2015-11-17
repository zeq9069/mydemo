package com.demo.druidTest.service;

import com.demo.druidTest.domain.User;


public interface UserService {

	public boolean save(User user);
	
	public User get(int id);
	
}
