package com.demo.druidTest.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.druidTest.domain.User;
import com.demo.druidTest.repository.UserDao;

@Repository
public class UserDaoImpl implements UserDao<User,Integer>{

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return this.sessionFactory.getCurrentSession();
	}
	
	@Override
	public Integer add(User user) {
		return (int)getSession().save(user);
	}

	@Override
	public boolean delete(Integer id) {
		Session session=getSession();
		try{
			session.delete(session.get(User.class,id));
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public boolean update(User user) {
		try{
			getSession().update(user);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public User findOne(Integer id) {
		return getSession().get(User.class, id);
	}

}
