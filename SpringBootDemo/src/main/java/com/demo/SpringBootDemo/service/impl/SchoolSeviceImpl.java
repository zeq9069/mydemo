package com.demo.SpringBootDemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.SpringBootDemo.domain.School;
import com.demo.SpringBootDemo.repository.SchoolRepository;
import com.demo.SpringBootDemo.service.SchoolService;


@Service
public class SchoolSeviceImpl implements SchoolService{

	@Autowired
	private SchoolRepository schoolRepository;
	
	@Transactional(readOnly=false)
	@Override
	public boolean create(School school) {
		return schoolRepository.create(school);
	}

	@Transactional(readOnly=true)
	@Override
	public School get(int id) {
		return schoolRepository.get(id);
	}

}
