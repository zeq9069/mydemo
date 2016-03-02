package com.kyrin.CAS.controller;

import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class LoginUser {
	
	@RequestMapping(value="cas/www/login",method=RequestMethod.GET)
	@ResponseBody
	public String login(){
		 Subject currentUser = SecurityUtils.getSubject();
		 CasToken auth=new CasToken("111");
		 currentUser.login(auth);
		 if(currentUser.isAuthenticated()){
			 System.out.println(""+currentUser.getSession().getAttribute("ticket"));
			 return "login";
		 }
		 
		 return "error";
	}

}
