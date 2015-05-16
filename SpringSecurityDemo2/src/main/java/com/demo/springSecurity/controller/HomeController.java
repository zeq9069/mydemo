package com.demo.springSecurity.controller;

import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.springSecurity.domain.UserInfo;

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
	
	@RequestMapping(value="user/info",method=RequestMethod.GET)
	@ResponseBody
	public String info() throws Exception{
		Object p=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(p instanceof UserInfo){
			UserInfo user=(UserInfo)p;
			return "username:"+user.getName()+"\n password:"+user.getPassword();
		}else{
			throw new Exception("登陆有误");
		}
	}
	
	
	
	@RequestMapping(value="loginfail",method=RequestMethod.GET)
	@ResponseBody
	public String loginfail(HttpServletRequest request) throws ServletException{
		return "登录失败："+SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	
	
	
	@ExceptionHandler(value={Exception.class})
	@ResponseBody
	public String exceptionHandler(Exception e){
		e.printStackTrace();
		return "Error ! The reason : "+e.getMessage();
	}

}
