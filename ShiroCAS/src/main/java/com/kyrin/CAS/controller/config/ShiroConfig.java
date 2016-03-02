package com.kyrin.CAS.controller.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import  org.apache.shiro.web.mgt.DefaultWebSecurityManager;

@Configuration
public class ShiroConfig {

 
	
	@Bean
	public Realm myCASRealm(){
		MyCASRealm realm=new MyCASRealm();
		realm.setDefaultRoles("ROLE_USER");
		realm.setCasServerUrlPrefix("http://localhost:9999/cas");
		realm.setCasService("http://localhost:9999/client/shiro-cas");
		return realm;
	}
	
	@Bean
	public CasSubjectFactory casSubjectFactory(){
		return new CasSubjectFactory();
	}
	
	@Bean
	public DefaultWebSecurityManager defaultWebSecurityManager(){
		DefaultWebSecurityManager dsm=new DefaultWebSecurityManager();
		dsm.setRealm(myCASRealm());
		dsm.setSubjectFactory(casSubjectFactory());
		return dsm;
	}
	
	@Bean
	public CasFilter casFilter(){
		CasFilter cf=new CasFilter();
		//ticket校验不通过
		cf.setFailureUrl("/page/error.jsp");
		return cf;
	}
	
	@Bean(name="shiroFilterFactoryBean")
	public ShiroFilterFactoryBean shiroFilterFactoryBean(){
		ShiroFilterFactoryBean sf=new ShiroFilterFactoryBean();
		sf.setSecurityManager(defaultWebSecurityManager());
		sf.setLoginUrl("http://localhost:9999/cas/login?service=http://localhost:9999/client/shiro_cas");
		sf.setSuccessUrl("/success");
		Map<String,Filter> filters=new HashMap<String,Filter>();
		filters.put("casFilter", casFilter());
		sf.setFilters(filters);
		sf.setFilterChainDefinitions("/casqqq=casFilter \n /styles/**= anon \n /**= anon");
		return sf;
	}
	
	
	//保证实现了Shiro内部lifecycle函数的bean执行 
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
		LifecycleBeanPostProcessor lb=new LifecycleBeanPostProcessor();
			return lb;
	}
			
	
	//<!--AOP式方法级权限检查 -->
	//<!--Enable Shiro Annotations for Spring-configured beans. Only run after -->
	//<!--the lifecycleBeanProcessor has run: -->
	@Bean
	@DependsOn(value="lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
		DefaultAdvisorAutoProxyCreator daap=new DefaultAdvisorAutoProxyCreator();
		daap.setProxyTargetClass(true);
		return daap;
	}
	
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
		AuthorizationAttributeSourceAdvisor auth=new AuthorizationAttributeSourceAdvisor();
		auth.setSecurityManager(defaultWebSecurityManager());
		return auth;
	}
	 

}
