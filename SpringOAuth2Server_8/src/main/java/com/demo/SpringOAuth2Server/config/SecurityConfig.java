package com.demo.SpringOAuth2Server.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.demo.SpringOAuth2Server.saltSource.MySaltSource;
import com.demo.SpringOAuth2Server.service.UserInfoService;

/**
 * 
 * security 配置
 * 
 * @author zhangerqiang
 *
 */

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled=true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private MySaltSource mySaltSource;
	
	
		@Autowired
		public void configure(AuthenticationManagerBuilder auth) throws Exception {

			//密码加密解密
			Md5PasswordEncoder md5=new Md5PasswordEncoder();
			DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
			daoAuthenticationProvider.setSaltSource(mySaltSource);
			daoAuthenticationProvider.setPasswordEncoder(md5);
			daoAuthenticationProvider.setUserDetailsService(userInfoService);
			
			auth.authenticationProvider(daoAuthenticationProvider);
			
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
					.loginPage("/login")
					.failureUrl("/login?error")
					.defaultSuccessUrl("/home/index")
					.usernameParameter("username")
					.passwordParameter("password")
					.loginProcessingUrl("/loginHandle")
				.and()
				.httpBasic();
		}


		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/resources/**");
		}
	}