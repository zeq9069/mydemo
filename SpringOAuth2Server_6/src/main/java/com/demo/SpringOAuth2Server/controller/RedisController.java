package com.demo.SpringOAuth2Server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/redis")
public class RedisController {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	
	@RequestMapping(value="read/{key}")
	@ResponseBody
	public String read(@PathVariable("key") String key){
		return stringRedisTemplate.boundHashOps("map").get(key).toString();
	}
	
	
	@RequestMapping(value="write/{key}/{value}",method=RequestMethod.GET)
	public void write(@PathVariable("key") String key,@PathVariable("value") String value){
		
		System.out.println(stringRedisTemplate==null?true:false);
		
		BoundHashOperations<String, String, String>  hh=stringRedisTemplate.boundHashOps("map");
		hh.putIfAbsent(key, value);
		
	}

}
