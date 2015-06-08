package com.demo.SpringOAuth2Server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.SpringOAuth2Server.service.HomeService;

@Controller
public class HomeController {

	HomeService homeService;

	@Autowired
	public  HomeController(HomeService homeService) {
		this.homeService=homeService;
	}
	
	
	@Secured(value={"ROLE_USER","ROLE_ADMIN"})
	@RequestMapping(value="/home/index")
	@ResponseBody
	public String home(){
		
		System.out.println(homeService.hello());

		return "home/index";
	}
	
	/**********home/client/***************/
	//@Secured(value={" #oauth2.isClient() and  #oauth2.hasScope('info_read')"})
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
		if(obj instanceof UserDetails){
			UserDetails userInfo=(UserDetails)obj;
			return "当前用户名："+userInfo.getUsername()+"，权限："+userInfo.getAuthorities();
		}
		return ""+obj;
	}
	
	
	//homeController 全局异常捕获处理
	@ExceptionHandler(value={AccessDeniedException.class})
	@ResponseBody
	public String exceptionHandler(Exception e){
		return "Error! The reason is "+e.getMessage();
	}
}
