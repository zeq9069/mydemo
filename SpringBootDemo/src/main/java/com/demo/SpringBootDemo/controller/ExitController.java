package com.demo.SpringBootDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.SpringBootDemo.applicationExit.ApplicationExit_3;

/**
 * 应用程序退出
 * @author zhangerqiang
 *
 */
@Controller
@RequestMapping("exit")
public class ExitController {

	@Autowired
	ApplicationContext applicationContext;
	@RequestMapping(value="foo",method=RequestMethod.GET)
	public void exit(){
		//SpringApplication.exit(applicationContext, null);
		int exitCode=SpringApplication.exit(applicationContext, new ApplicationExit_3());
		System.out.println("退出码："+exitCode);
	}

}
