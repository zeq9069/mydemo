package com.demo.druidTest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.druidTest.service.UserService;

@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	private UserService userServiceImpl;
	
	@RequestMapping(value="find/{id}",method=RequestMethod.GET)
	@ResponseBody
	public String find(@PathVariable int id){
		return userServiceImpl.get(id).toString();
	}
	
}
