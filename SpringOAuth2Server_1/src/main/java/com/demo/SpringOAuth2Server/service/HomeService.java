package com.demo.SpringOAuth2Server.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

@Component
public class HomeService {
	
	//@Secured(value={"ROLE_USER1"})
	public String hello(){
		return "Hello";
	}

}
