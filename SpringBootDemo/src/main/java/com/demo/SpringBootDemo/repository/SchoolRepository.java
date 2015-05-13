package com.demo.SpringBootDemo.repository;

import com.demo.SpringBootDemo.domain.School;

public interface SchoolRepository {

	public boolean create(School school);
	
	public School get(int id);
}
