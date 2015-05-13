package com.demo.SpringBootDemo.resource;

import org.springframework.stereotype.Component;

import com.demo.SpringBootDemo.controller.SampleController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class UserResourceAssembler {
	
	public UserResource toResource(Users user){
		UserResource res=new UserResource();
		res.id=user.getId();
		res.name=user.getName();
		res.email=user.getEmail();
		res.add(linkTo(SampleController.class).slash("users").slash(user).withSelfRel());
		return res;
	}

}
