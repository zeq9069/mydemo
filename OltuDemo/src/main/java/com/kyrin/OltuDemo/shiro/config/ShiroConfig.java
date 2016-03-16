package com.kyrin.OltuDemo.shiro.config;

import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import  org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import com.kyrin.OltuDemo.realm.UserRealm;

@Configuration
public class ShiroConfig {

	private String cookie_domain=".oauth.com";
	
	private String session_redis_key_prefix="oltu:spring:sessions";
	
	
	@Bean
	public Realm myRealm(){
		UserRealm realm=new UserRealm();
		return realm;
	}
	
	@Bean
	public DefaultWebSubjectFactory defaultWebSubjectFactory(){
		return new CasSubjectFactory();
	}
	
	@Bean
	public SessionDAO sessionDAO(){
		RedisSessionDao redisDao=new RedisSessionDao();
		redisDao.setSpring_session_prefix(session_redis_key_prefix);
		return redisDao;
	}
	
	/**
	 * redis 共享session , 修改session的共享域 
	 * @return
	 */
	@Bean
	public DefaultWebSessionManager defaultWebSessionManager(){
		DefaultWebSessionManager d=new DefaultWebSessionManager();
		d.getSessionIdCookie().setDomain(cookie_domain);
		d.setSessionDAO(sessionDAO());
		return d;
	}
	
	
	
	@Bean
	public DefaultWebSecurityManager defaultWebSecurityManager(){
		DefaultWebSecurityManager dsm=new DefaultWebSecurityManager();
		dsm.setRealm(myRealm());
		dsm.setSubjectFactory(defaultWebSubjectFactory());
		dsm.setSessionManager(defaultWebSessionManager());
		return dsm;
	}
	
	 
	@Bean(name="shiroFilterFactoryBean")
	public ShiroFilterFactoryBean shiroFilterFactoryBean(){
		ShiroFilterFactoryBean sf=new ShiroFilterFactoryBean();
		sf.setSecurityManager(defaultWebSecurityManager());
		sf.setSuccessUrl("/success");
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
