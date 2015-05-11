package com.demo.SpringBootTest1.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.SpringBootTest1.dao.UserDao;
import com.demo.SpringBootTest1.domain.User;

@Repository
public class UserDaoImpl implements UserDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	public Session getSession(){
		return this.sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(User user) {
		this.getSession().save(user);
	}

	@Override
	public User get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
