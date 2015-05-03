package com.demo.AspectJDemo.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.AspectJDemo.annotation.ChangeFor;
import com.demo.AspectJDemo.datasource.DynamicDataSource;
import com.demo.AspectJDemo.repository.UserReponsitory;
import com.demo.AspectJDemo.service.UserService;


@Service("userService")
public class UserServiceImpl implements UserService{

	private static Logger logger=Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserReponsitory userReponsitory;
	
	@ChangeFor
	@Transactional(readOnly=false)
	public String create() {
		
		logger.info("Starting create a new User !");
		try{
		userReponsitory.create();
		return "success";
		}finally{
			DynamicDataSource.recover();
		}
	}
	
	@Transactional(readOnly=false)
	public void delete(String id) {
		try{
			DynamicDataSource.changeFor("slave");
		logger.info("Starting dalete a new User !");
		userReponsitory.create();
		}finally{
			DynamicDataSource.recover();
		}
	}

	@ChangeFor(value="slave")
	@Transactional(readOnly=false)
	public void update() {
		logger.info("Starting update a new User !");
	}
	@ChangeFor(value="slave")
	@Transactional(readOnly=true)
	public void search() {
		logger.info("Starting search a new User !");
	}

}
