package com.demo.AspectJDemo.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.AspectJDemo.annotation.ChangeFor;
import com.demo.AspectJDemo.annotation.DataSourceDistribute;
import com.demo.AspectJDemo.annotation.DataSourceEntity;
import com.demo.AspectJDemo.annotation.DataSourceGroup;
import com.demo.AspectJDemo.annotation.Group;
import com.demo.AspectJDemo.repository.UserReponsitory;
import com.demo.AspectJDemo.service.UserService;

//@DataSourceDistribute和@changeFor同时使用时，符合“就近原则”，
//@changeFor会覆盖@DataSourceDistribute

@Service("userService")
//@DataSourceDistribute(value={@DataSourceEntity(methodPattern="delete*|update*"),
//		@DataSourceEntity(dataSource="slave",methodPattern="find*")})

@DataSourceGroup(groups={
		@Group(groupName="slave-group",methodPattern="delete*"),
		@Group(groupName="master-group",methodPattern="create*")
		})
public class UserServiceImpl implements UserService{

	private static Logger logger=Logger.getLogger(UserServiceImpl.class);
	
	
	@Autowired
	private UserReponsitory userReponsitory;
	
	//@ChangeFor
	@Transactional(readOnly=false)
	public  String create() {
		logger.info("Starting create a new User !");
		userReponsitory.create();
		return "success";
	}

	@Transactional(readOnly=false)
	public  void delete(String id) {
		logger.info("Starting dalete a new User !");
		userReponsitory.create();
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
