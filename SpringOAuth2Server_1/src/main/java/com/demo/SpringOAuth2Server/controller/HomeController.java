package com.demo.SpringOAuth2Server.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	
	@Secured(value={"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping(value="home/index",method=RequestMethod.GET)
	@ResponseBody
	public String home(){
		
		System.out.println(homeService.hello());

		return "home/index";
	}
	
	/**********home/client/***************/
	
	@RequestMapping(value="home/client/info")
	@ResponseBody
	public String client(){	
		return "client_info";
	}
	
	/********************************/
	@RequestMapping(value="/main/user/info")
	@ResponseBody
	public String userInfo(){	
		//获取当前登录用户的信息
		Object obj=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return ""+obj;
	}
	
	@RequestMapping(value="/logout")
	@ResponseBody
	public String logout(HttpServletRequest request) throws ServletException{	
		request.logout();
		return "成功退出";
	}
	
	
	
	
	//homeController 全局异常捕获处理
	@ExceptionHandler(value={AccessDeniedException.class})
	@ResponseBody
	public String exceptionHandler(Exception e){
		return "Error! The reason is "+e.getMessage();
	}
	
	
}
