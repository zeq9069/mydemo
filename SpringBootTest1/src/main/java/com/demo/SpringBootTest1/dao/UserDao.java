package com.demo.SpringBootTest1.dao;

import com.demo.SpringBootTest1.domain.User;

public interface UserDao {
	
	public void create(User user);
	
	public User get(int id);

}
