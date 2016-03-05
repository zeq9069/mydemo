package com.kyrin.OltuDemo.service;



import java.util.Date;

import com.kyrin.OltuDemo.model.ClientInfo;


public interface OAuthService {
	
	
	public String getAccessToken(String clientid,String username);
	
	public void saveAccessToken(String accessToken,String refreshToken,String clientId,String username,Date expireIn);
	
	public void saveAuthorizationCode(String code,String username);
	
	public String getAuthorzationCode(String code);
	
	public ClientInfo getClientInfo(String clientId);
	
	public boolean checkClientSecret(String clientId,String clientSecret);
	
	public boolean checkClientId(String clientId);
	
	public boolean checkRedirectUrl(String clientId,String redirectUrl);
	
}
