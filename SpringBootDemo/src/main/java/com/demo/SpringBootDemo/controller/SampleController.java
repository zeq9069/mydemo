package com.demo.SpringBootDemo.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.SpringBootDemo.resource.UserResource;
import com.demo.SpringBootDemo.resource.UserResourceAssembler;
import com.demo.SpringBootDemo.resource.Users;



@Controller
public class SampleController 
{
	@Value("${name}")
	private String name;
	
	@Autowired
	private UserResourceAssembler userResourceAssembler;
	
	
	@RequestMapping(value="/hello",method=RequestMethod.GET,produces=MediaType.APPLICATION_ATOM_XML_VALUE)
	@ResponseBody
	public String home(){
		return "hello world !"+name;
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String welcome(Map<String, Object> model){
		model.put("time", new Date());
		model.put("message", this.name);
		return "welcome";
	}
	
	@RequestMapping(value="kyrin",method=RequestMethod.GET,produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public String users() throws Exception{
		
		return "kyrin";
	}
	
	//hetaoas 构造restful服务 例子
	@RequestMapping(value="users/{id}",method=RequestMethod.GET,produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserResource> users(@PathVariable("id") Long id) throws Exception{
		Assert.isTrue(id > 0,"id must > 0");
		Users u=new Users(100L,"kyrin","906928204@qq.com");
		u.setId(id);
		//构造资源
		//Resource<String> res=new Resource<String>(value,linkTo(SamleController.class).slash("users").slash(page).withSelfRel());
		UserResource ur=userResourceAssembler.toResource(u);
		return new ResponseEntity<UserResource>(ur,HttpStatus.OK);
	}
	
	
	
	//处理所有controller抛出的异常
	@ExceptionHandler(value={Exception.class})
	public ResponseEntity<String> exceptions(Exception e){
		return new ResponseEntity<String>(String.format("error resean: %s",e.getMessage()),HttpStatus.BAD_REQUEST);
	}
	
	
	
}
