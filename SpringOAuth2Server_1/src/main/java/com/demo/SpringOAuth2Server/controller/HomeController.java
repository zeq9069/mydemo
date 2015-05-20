package com.demo.SpringOAuth2Server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.SpringOAuth2Server.service.HomeService;

@Controller
public class HomeController {

	HomeService homeService;

	@Autowired
	public  HomeController(HomeService homeService) {
		this.homeService=homeService;
	}
	
	
	//@Secured(value={"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping(value="home/index",method=RequestMethod.GET)
	@ResponseBody
	public String home(){
		
		System.out.println(homeService.hello());

		return "home/index";
	}
	
	
		@RequestMapping(value="home/client/info")
		@ResponseBody
		public String client(){
			
			
			return "client_info";
		}
	
	
	@ExceptionHandler(value={AccessDeniedException.class})
	@ResponseBody
	public String exceptionHandler(Exception e){
		return "Error! The reason is "+e.getMessage();
	}
	
	
}
