package com.demo.SpringOAuth2Server.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

/**
 * oauth2 server 配置
 * 
 * @author zhangerqiang
 *
 */
@Configuration
public class OAuth2ServerConfig {
	
	private static boolean reuseRefreshToken=false;//每个refresh_token只能使用一次
	
	//资源服务配置
	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfig extends ResourceServerConfigurerAdapter{

		@Override
		public void configure(ResourceServerSecurityConfigurer resources)
				throws Exception {
			super.configure(resources);
			resources.resourceId("resource_1").stateless(false);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.and()
				.requestMatchers().antMatchers("/home/client/**")
				.and()
				.authorizeRequests()
					.antMatchers("/home/client/**").access("#oauth2.isClient() or hasRole('ROLE_USER') or #oauth2.hasScope('read')")
					.anyRequest().permitAll();
		}
	}
	
	//授权服务配置
	@Configuration
	@EnableAuthorizationServer
	protected static class OAuth2Server extends AuthorizationServerConfigurerAdapter{
		
		@Autowired
		private static TokenEnhancer tokenEnhancer;
		
		@Autowired
		@Qualifier("dataSource")
		private  DataSource dataSource;
		
		@Override
		public void configure(AuthorizationServerSecurityConfigurer security)
				throws Exception {
			
			security.realm("oauth/client");//默认的（可选）需要授权才能存取的范围
			security.allowFormAuthenticationForClients();//解决 异常：Full authentication is required to access this resource
			security.checkTokenAccess("permitAll()");//否则，访问/oauth/check_token 会被禁止访问,因为默认是denyAll()
			//http://stackoverflow.com/questions/26750999/spring-security-oauth2-check-token-endpoint
		}

		
		@Override
		public void configure(ClientDetailsServiceConfigurer clients)
				throws Exception {
			clients
				.jdbc(dataSource);
		}
		
		//使用自带的jdbc来操作client信息，这样自定义的access_token和refresh_token的过期时间就失效了，自动使用数据库中的时间
		@Bean
		public ClientDetailsService clientDetailsService(){
			JdbcClientDetailsService clientDetailsService=new JdbcClientDetailsService(dataSource);
			return clientDetailsService;
		}

		@Bean
		public TokenStore tokenStore(){
			//InMemoryTokenStore in=new InMemoryTokenStore();
			JdbcTokenStore in=new JdbcTokenStore(dataSource);
			return in;
		}
		
		//自定义approvalStore成bean，在loginController可以获取
		@Bean
		public ApprovalStore approvalStore(){
			TokenApprovalStore approvalStore=new TokenApprovalStore();
			approvalStore.setTokenStore(tokenStore());
			return approvalStore;
		}
		
		//此bean一定要在configure方法前运行加载，否则将使用默认的tokenServices
		@Bean
		public AuthorizationServerTokenServices tokenServices(){
			DefaultTokenServices tokenServices = new DefaultTokenServices();
			tokenServices.setTokenStore(tokenStore());
			tokenServices.setSupportRefreshToken(true);
			tokenServices.setReuseRefreshToken(reuseRefreshToken);
			tokenServices.setClientDetailsService(clientDetailsService());
			tokenServices.setTokenEnhancer(tokenEnhancer);
			return  tokenServices;
		}
		
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints)
				throws Exception {
			endpoints.setClientDetailsService(clientDetailsService());	//将client信息存储到内存，也是默认的（可不设置）
			endpoints.reuseRefreshTokens(reuseRefreshToken);			
			endpoints.tokenStore(tokenStore());
			endpoints.tokenServices(tokenServices());
			endpoints.approvalStore(approvalStore());
		}
	}
}
