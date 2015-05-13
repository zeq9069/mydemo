package com.demo.SpringBootDemo.service;


import com.demo.SpringBootDemo.domain.School;

public interface SchoolService {
	
	
	public boolean create(School school);
	
	public School get(int id);

}
