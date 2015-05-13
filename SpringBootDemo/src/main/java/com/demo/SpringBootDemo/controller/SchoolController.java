package com.demo.SpringBootDemo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.SpringBootDemo.domain.School;
import com.demo.SpringBootDemo.service.SchoolService;

@RestController
@RequestMapping("/schools")
public class SchoolController {

	@Autowired
	private SchoolService schoolService;
	
	@RequestMapping(value="1",method=RequestMethod.GET)
	public String get(){
		schoolService.create(new School("郑州大学"));
		return "success";
	}
	
	
}
