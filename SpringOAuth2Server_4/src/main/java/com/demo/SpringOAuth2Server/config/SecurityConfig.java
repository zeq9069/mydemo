package com.demo.SpringOAuth2Server.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.demo.SpringOAuth2Server.service.UserInfoService;

/**
 * 
 * security 配置
 * 
 * @author zhangerqiang
 *
 */

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserInfoService userInfoService;
	
		@Autowired
		public void configure(AuthenticationManagerBuilder auth) throws Exception {

			auth.userDetailsService(userInfoService);
			//添加以下信息会报错
				//auth.jdbcAuthentication()
					//.dataSource(dataSource);
						//.withUser("kyrin").password("123").roles("USER","ADMIN");
				
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.
				authorizeRequests()
					.antMatchers("/main/**").hasAnyRole("USER","ADMIN")
					.anyRequest().permitAll()
				.and()
				//添加对csrf的关闭处理，否则在请求/oauth/authorize时，出现csrf相关的异常
				.csrf()
                	.requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                	.disable()
				.formLogin()
				.and()
				.httpBasic();
		}


		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/resources/**");
		}
	}
