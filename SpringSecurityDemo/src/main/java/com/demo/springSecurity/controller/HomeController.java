package com.demo.springSecurity.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("home")
public class HomeController {
	
	@RequestMapping(value="index",method=RequestMethod.GET)
	@ResponseBody
	public String index(HttpServletRequest request) throws ServletException{
		request.logout();
		return "home";
	}
	
	@RequestMapping(value="login",method=RequestMethod.GET)
	@ResponseBody
	public String login(HttpServletRequest request) throws ServletException{

		return "这是自定义的一个登陆页面:login";
	}
	
	
	@ExceptionHandler(value={Exception.class})
	@ResponseBody
	public String exceptionHandler(Exception e){
		e.printStackTrace();
		return "Error ! The reason : "+e.getMessage();
	}

}
