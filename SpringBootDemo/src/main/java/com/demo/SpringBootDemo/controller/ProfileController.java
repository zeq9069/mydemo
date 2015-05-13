package com.demo.SpringBootDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.SpringBootDemo.other.ServerProperty;

/**
 * profile练习
 * @author zhangerqiang
 *
 */
@Controller
@RequestMapping("profile")
public class ProfileController {

	@Value("${url}")
	private String url;
	
	@Autowired
	private ServerProperty serverProperty;
	
	@RequestMapping(value="url",method=RequestMethod.GET)
	@ResponseBody
	public String url(){
		return url;
	}
	
	@RequestMapping(value="servers",method=RequestMethod.GET)
	@ResponseBody
	public List<String> servers(){
		return serverProperty.getServer();
	}
	
	@ExceptionHandler(value={Exception.class})
	public void exceptionHandler(Exception e){
		System.out.println(String.format("Error the reason : %s", e.getMessage()));
	}
	
	
}
