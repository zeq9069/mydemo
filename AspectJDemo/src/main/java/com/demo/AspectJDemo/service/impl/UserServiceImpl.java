package com.demo.AspectJDemo.service.impl;

import org.springframework.stereotype.Service;

import com.demo.AspectJDemo.annotation.ChangeFor;
import com.demo.AspectJDemo.service.UserService;


@Service("userService")
public class UserServiceImpl implements UserService{

	@ChangeFor(value="master")
	public void create() {
		System.out.println("Starting create a new User !");
	}
	
	@ChangeFor(value="master")
	public void delete() {
		System.out.println("Starting dalete a new User !");
	}

	@ChangeFor(value="master")
	public void update() {
		System.out.println("Starting update a new User !");
	}
	@ChangeFor(value="slave")
	public void search() {
		System.out.println("Starting search a new User !");
	}

}
