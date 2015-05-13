package com.demo.SpringBootDemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 演示 随机数在application.properties中的配置
 * 
 * @author zhangerqiang
 *
 */
@Controller
@RequestMapping("random")
public class RandomValuePropertySourceController {
	
	@Value("${my.secret}")
	private String secret;
	
	@Value("${my.number}")
	private String number;
	
	@Value("${my.bignumber}")
	private String bignumber;
	
	@Value("${my.number.less.than.ten}")
	private String lessthanten;
	
	@Value("${my.number.in.range}")
	private String rangenumber;
	
	
	@RequestMapping(value="secret",method=RequestMethod.GET)
	@ResponseBody
	public String secret(){
		return String.format("secret=%s , number=%s , bignumber=%s , lessthanten=%s , rangenumber=%s ", secret,number,bignumber,lessthanten,rangenumber);
	}

}
