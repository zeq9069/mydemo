package com.demo.SpringBootDemo.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.SpringBootDemo.domain.School;
import com.demo.SpringBootDemo.repository.SchoolRepository;

@Repository
public class SchoolRepositoryImpl implements SchoolRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return this.sessionFactory.getCurrentSession();
	}
	
	@Override
	public boolean create(School school) {
		try{ 
			System.out.println("==="+school.getName());
			
		this.getSession().save(school);
		return true;
		}catch(Exception e){
			System.out.println(String.format("The reason : %s",e.getMessage()));
			return false;
		}
	}

	@Override
	public School get(int id) {
		Criteria cri=this.getSession().createCriteria(School.class);
		cri.add(Restrictions.eq("id", id));
		return (School) cri.uniqueResult();
	}

}
