package com.demo.SpringOAuth2Server.client.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.client.token.JdbcClientTokenServices;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * OAuth2.0 客户端
 * 
 * @author lenovo
 *
 */
@Configuration
@EnableOAuth2Client
public class OAuth2ClientConfig {
	
	@Value("${accessTokenUri}")
	private String accessTokenUri;
	
	@Value("${userAuthorizationUri}")
	private String userAuthorizationUri;
	
	
	@Bean
	public OAuth2ProtectedResourceDetails  sina(){
		AuthorizationCodeResourceDetails acr=new AuthorizationCodeResourceDetails();
		acr.setId("oauth2/app-sina");
		acr.setClientId("test");
		acr.setClientSecret("test");
		acr.setUserAuthorizationUri(userAuthorizationUri);
		acr.setAccessTokenUri(accessTokenUri);
		acr.setScope(Arrays.asList("read","write"));
		return acr;
	}
	
	@Bean
	public OAuth2ProtectedResourceDetails  qq(){
		AuthorizationCodeResourceDetails acr=new AuthorizationCodeResourceDetails();
		acr.setId("oauth2/app-qq");
		acr.setClientId("test");
		acr.setClientSecret("test");
		acr.setUserAuthorizationUri(userAuthorizationUri);
		acr.setAccessTokenUri(accessTokenUri); 
		acr.setScope(Arrays.asList("read","write"));
		return acr;
	}
	
	
	@Bean
	public OAuth2ProtectedResourceDetails  weixin(){
		AuthorizationCodeResourceDetails acr=new AuthorizationCodeResourceDetails();
		acr.setId("oauth2/app-weixin");
		acr.setClientId("test");
		acr.setClientSecret("test");
		acr.setUserAuthorizationUri(userAuthorizationUri);
		acr.setAccessTokenUri(accessTokenUri);
		acr.setScope(Arrays.asList("read","write"));
		return acr;
	}
	
	@Bean
	public OAuth2RestOperations sinaRestTemplate(OAuth2ClientContext oauth2ClientContext) {
		OAuth2RestTemplate ort=new OAuth2RestTemplate(sina(), oauth2ClientContext);
		JdbcClientTokenServices d;
		DefaultOAuth2AccessToken r;
		DefaultOAuth2RefreshToken g;
		AuthorizationCodeAccessTokenProvider f;
		return ort; 
  	}

}
