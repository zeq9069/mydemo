package com.demo.AspectJDemo.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.AspectJDemo.domain.User;
import com.demo.AspectJDemo.repository.UserReponsitory;

/**
 * ***********************
 * 
 *
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年5月3日]
 *
 */
@Repository
public class UserReponsitoyImpl implements UserReponsitory{

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public String create() {
		User user=new User();
		user.setName("kyrin");
		user.setPassword("123456");
		int id=(int) this.getSession().save(user);
		return ""+id;
	}

	@Override
	public void delete(String id) {
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void search() {
		// TODO Auto-generated method stub
		
	}

}
