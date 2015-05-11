package com.demo.SpringBootTest1.service;

import com.demo.SpringBootTest1.domain.User;

public interface UserService {
	
	public void create(User user);
	
	public User get(int id);

}
