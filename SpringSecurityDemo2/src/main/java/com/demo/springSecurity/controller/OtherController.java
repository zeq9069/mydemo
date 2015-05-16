package com.demo.springSecurity.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OtherController {

	@RequestMapping(value="/loginfail",method=RequestMethod.GET)
	@ResponseBody
	public String loginfail(){
		return ""+SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpServletRequest request) throws ServletException{
		request.logout();
		return "logout";
	}

	@ExceptionHandler(value={Exception.class})
	@ResponseBody
	public String exceptionHandler(Exception e){
		e.printStackTrace();
		return "Error ! The reason : "+e.getMessage();
	}
}
