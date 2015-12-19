package com.demo.springSecurity.securityConfig;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
		/*     1,基于内存的认证
		 		auth
					.inMemoryAuthentication() 
						.withUser("kyrin").password("123456").roles("USER").roles("USER").and()
						.withUser("admin").password("root").roles("USER","ADMIN");
		 */
		
		//2，基于数据库的认证
		auth
			.jdbcAuthentication()
			.dataSource(dataSource)
			//.withDefaultSchema()   //默认db schema
			.withUser("kyrin").password("123456").roles("USER").roles("USER").and()
			.withUser("admin").password("root").roles("USER","ADMIN");
		
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				//.anyRequest().authenticated()//任何请求都必须授权
				.antMatchers("/resources/**","/login*","/regist*","about").permitAll() //匹配的请求授权给所有人可以访问
				.antMatchers("/admin/**").hasRole("ADMIN") //ADMIN用户才有权访问
				.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')") 
				.anyRequest().authenticated()//剩余的URL只要是被认证的用户都可以访问
				.and()
			.formLogin()
				.loginPage("/home/login")//自定义登陆页面，授权给任何人可以登录
				.permitAll()
				.and()
			.httpBasic();
	}
	
	
}
