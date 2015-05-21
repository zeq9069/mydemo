package com.demo.SpringOAuth2Server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth
				.inMemoryAuthentication()
					.withUser("kyrin").password("123").roles("USER");
		}
		
		//	org.springframework.security.web.access.expression.WebSecurityExpressionRoot
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			//httpSecurity 的功能类似于 xml 中的 <http> 标签
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
