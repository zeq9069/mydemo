package com.demo.SpringBootTest1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.SpringBootTest1.aware.TestAware;
import com.demo.SpringBootTest1.domain.User;
import com.demo.SpringBootTest1.service.UserService;

@Controller
@RequestMapping(value="/home")
public class HomeController {
	
	@Value(value="${name}")
	private String name;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="main",method=RequestMethod.GET)
	@ResponseBody
	public String main(){
		return "main";
	}
	
	@RequestMapping(value="index",method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute("name",name);
		return "index";
	}
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	@ResponseBody
	public String create(){
		try{
			userService.create(new User("Kyrin","kyrincloud@qq.com"));
		}catch(Exception e){
			e.printStackTrace();
		}
		return "success!";
	} 
	
	@RequestMapping(value="foo",method=RequestMethod.GET)
	public void foo(Model model){
		throw new RuntimeException("程序退出！");
	}
	
	
	@RequestMapping(value="testAware",method=RequestMethod.GET)
	@ResponseBody
	public String testAware(){
		return TestAware.getInstance().test();
	}

	//处理controller中所有抛出的异常
	@ExceptionHandler(value={Exception.class})
	public void exceptionHancler(Exception e){
		System.out.println(String.format("Error! The reason : %s", e.getMessage()));
	}
	
	
	
	

}
