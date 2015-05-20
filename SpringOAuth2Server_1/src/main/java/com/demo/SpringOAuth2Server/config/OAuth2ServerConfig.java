package com.demo.SpringOAuth2Server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * oauth2 server 配置
 * 
 * @author zhangerqiang
 *
 */
@Configuration
public class OAuth2ServerConfig {
	
	
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

		@Override
		public void configure(AuthorizationServerSecurityConfigurer security)
				throws Exception {
			//解决 异常：Full authentication is required to access this resource
			security.realm("oauth/client");//默认的（可选）需要授权才能存取的范围
			security.allowFormAuthenticationForClients();
			security.checkTokenAccess("permitAll()");//否则，访问/oauth/check_token 会被禁止访问,因为默认是denyAll()
			//http://stackoverflow.com/questions/26750999/spring-security-oauth2-check-token-endpoint
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients)
				throws Exception {
			clients
				.inMemory()
					.withClient("test")
						.secret("test")//如果加上secret的话，请求token时，必须加上client_secret=test
						.authorizedGrantTypes("password","authorization_code","refresh_token")//,"client_credentials"
						.resourceIds("resource_1")
						.authorities("ROLE_CLIENT")
						.redirectUris("http://test.com")
						.scopes("read","write");
			
		}

		//Configure the properties and enhanced functionality of the Authorization Server endpoints.
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints)
				throws Exception {
			endpoints.setClientDetailsService(new InMemoryClientDetailsService());//将client信息存储到内存，也是默认的（可不设置）
			//endpoints.tokenStore();
			//endpoints.approvalStore();
			endpoints.reuseRefreshTokens(false);//true代表一个refresh_token可以永久使用，false代表没使用一次refresh_token,就重新颁发一次
			//如何修改token的过期时间？自定义AuthorizationServerEndpointsConfigurer中的tokenServices
		
		}
		
	}
	
}
