package com.demo.SpringOAuth2Server.config;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
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
	
	private static boolean reuseRefreshToken=false;//每个refresh_token只能使用一次
	private static final int ACCESS_TOKEN_VALIDATE_SECONDS=60*60*12;  //access_toke有效期12小时，也是默认的时间
	private static final int REFRESH_TOKEN_VALIDATE_SECONDS=60*60*24*7;//refresh_token有效期7天，默认是30天
	
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
		private static ClientDetailsService clientDetailsService;
		
		@Autowired
		private static TokenEnhancer tokenEnhancer;
		
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
				.inMemory()
					.withClient("test")
						.secret("test")								//如果加上secret的话，请求token时，必须加上client_secret=test
						.authorizedGrantTypes("password","authorization_code","refresh_token")//"client_credentials"
						.resourceIds("resource_1")
						.authorities("ROLE_CLIENT")
						.redirectUris("http://test.com")
						.scopes("read","write");
			
		}

		@Bean
		public TokenStore tokenStore(){
			InMemoryTokenStore in=new InMemoryTokenStore();
			return in;
		}
		
		//修改token的过期时间，自定义AuthorizationServerEndpointsConfigurer中的tokenServices
		//此bean一定要在configure方法前运行加载，否则将使用默认的tokenServices
		@Bean
		public AuthorizationServerTokenServices tokenServices(){
			DefaultTokenServices tokenServices = new DefaultTokenServices();
			tokenServices.setTokenStore(tokenStore());
			tokenServices.setSupportRefreshToken(true);
			tokenServices.setReuseRefreshToken(reuseRefreshToken);
			tokenServices.setClientDetailsService(clientDetailsService);
			tokenServices.setTokenEnhancer(tokenEnhancer);
			tokenServices.setAccessTokenValiditySeconds(ACCESS_TOKEN_VALIDATE_SECONDS);		//自定义accessToken的有效期，默认是12小时
			tokenServices.setRefreshTokenValiditySeconds(REFRESH_TOKEN_VALIDATE_SECONDS);				//自定义refresh_token有效期，默认30天
			return  tokenServices;
		}
		
		
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints)
				throws Exception {
			endpoints.setClientDetailsService(clientDetailsService);	//将client信息存储到内存，也是默认的（可不设置）
			endpoints.reuseRefreshTokens(reuseRefreshToken);			
			endpoints.tokenStore(tokenStore());
			endpoints.tokenServices(tokenServices());
		}
	}
}
