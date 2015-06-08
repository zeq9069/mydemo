package com.demo.SpringOAuth2Server.client.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.client.token.JdbcClientTokenServices;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import com.demo.SpringOAuth2Server.client.consume.ConsumeWeixinAuthorizationCodeAccessTokenProvider;

/**
 * oauth2.0 client  config
 * 
 * @author zhangerqiang
 *
 */
@Configuration
@EnableOAuth2Client
public class OAuth2ClientConfig {
	
	
	@Autowired
	private DataSource dateSource;
	
	@Autowired
	private OAuth2ClientContext oauth2ClientContext;
	
	@Value("${sinaAccessTokenUri}") 
	private String sinaAccessTokenUri;
	
	@Value("${sinaUserAuthorizationUri}") 
	private String sinaUserAuthorizationUri;
	

	@Value("${weixinAccessTokenUri}") 
	private String weixinAccessTokenUri;
	
	@Value("${weixinUserAuthorizationUri}") 
	private String weixinUserAuthorizationUri;
	

	@Value("${qqAccessTokenUri}") 
	private String qqAccessTokenUri;
	
	@Value("${qqUserAuthorizationUri}") 
	private String qqUserAuthorizationUri;
	
	
	
	public OAuth2ProtectedResourceDetails sina(){
		AuthorizationCodeResourceDetails r=new AuthorizationCodeResourceDetails();
		r.setId("oauth/app-sina");
		r.setClientId("test");
		r.setClientSecret("test");
		r.setAccessTokenUri(sinaAccessTokenUri);
		r.setUserAuthorizationUri(sinaUserAuthorizationUri);
		r.setUseCurrentUri(false);
		r.setScope(Arrays.asList("info_read","info_write","read"));
		return r;
	}
	
	public OAuth2ProtectedResourceDetails weixin(){
		AuthorizationCodeResourceDetails r=new AuthorizationCodeResourceDetails();
		r.setId("oauth/app-sina");
		r.setClientId("test");
		r.setClientSecret("test");
		r.setAccessTokenUri(weixinAccessTokenUri);
		r.setUserAuthorizationUri(weixinUserAuthorizationUri);
		r.setUseCurrentUri(false);
		r.setScope(Arrays.asList("info_read","info_write"));
		return r;
	}
	
	public OAuth2ProtectedResourceDetails qq(@Value("${qqAccessTokenUri}") String accessTokenUri,
			@Value("${qqUserAuthorizationUri}") String userAuthorizationUri){
		AuthorizationCodeResourceDetails r=new AuthorizationCodeResourceDetails();
		r.setId("oauth/app-sina");
		r.setClientId("test");
		r.setClientSecret("test");
		r.setAccessTokenUri(qqAccessTokenUri);
		r.setUserAuthorizationUri(qqUserAuthorizationUri);
		r.setUseCurrentUri(false);
		r.setScope(Arrays.asList("info_read","info_write"));
		return r;
	}
	
	public ClientTokenServices clientTokenServices(){
		JdbcClientTokenServices j=new JdbcClientTokenServices(dateSource);
		return j;
	}
	
	@Bean
	public OAuth2RestTemplate sinaRestTemplate( ) {
		OAuth2RestTemplate sina=new OAuth2RestTemplate(sina(), new DefaultOAuth2ClientContext());
		return sina;
	}
	
	@Bean
	public OAuth2RestTemplate weixinRestTemplate( ) {
		OAuth2RestTemplate weixin=new OAuth2RestTemplate(weixin(), new DefaultOAuth2ClientContext());
		AccessTokenProviderChain provider = new AccessTokenProviderChain(Arrays.asList(new ConsumeWeixinAuthorizationCodeAccessTokenProvider()));
		provider.setClientTokenServices(clientTokenServices());
		weixin.setAccessTokenProvider(provider);
		return weixin;
	}
	
	@Bean
	public OAuth2RestTemplate qqRestTemplate( ) {
		OAuth2RestTemplate qq=new OAuth2RestTemplate(sina(), new DefaultOAuth2ClientContext());
		return qq;
	}
	
	
	
	
	

}
