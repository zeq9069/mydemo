package com.demo.springSecurity.webSecurityExpressionHandler;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.stereotype.Component;

/**
 *前段标签获取 permission 相关
 * 
 * 定义DefaultWebSecurityExpressionHandler ，改变其默认的permissionEvaluator为自定义的permissionEvaluator
 * 
 * @author kyrin
 *
 */

@Component
@Configuration
public class MyWebSecurityExpressionHandler{
	
	@Resource
	private PermissionEvaluator permissionEvaluator;
	
	@Bean
	public DefaultWebSecurityExpressionHandler WebSecurityExpressionHandler()
	{
		DefaultWebSecurityExpressionHandler d=new DefaultWebSecurityExpressionHandler();
		d.setPermissionEvaluator(permissionEvaluator);//permissionEvaluator更改权限解释器，默认是DenyAllPermissionEvaluator，拒绝所有
		//d.setExpressionParser(expressionParser);//改变jsp标签使用的表达式解析器，默认sp-el表达式
		return d;
	}
	
	
}
