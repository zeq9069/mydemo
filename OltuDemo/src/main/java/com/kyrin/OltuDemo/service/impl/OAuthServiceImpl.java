package com.kyrin.OltuDemo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kyrin.OltuDemo.model.AccessTokenInfo;
import com.kyrin.OltuDemo.model.ClientInfo;
import com.kyrin.OltuDemo.respository.AccessTokenRespository;
import com.kyrin.OltuDemo.respository.ClientInfoRespository;
import com.kyrin.OltuDemo.service.OAuthService;

/**
 * oauth service impl
 * @author kyrin
 *
 */
@Service
public class OAuthServiceImpl implements OAuthService{
	
	@Autowired
	private ClientInfoRespository clientInfoRepository;
	
	@Autowired
	private AccessTokenRespository accessTokenRepository;
	
	//授权码缓存
	Map<String,String> cache=new ConcurrentHashMap<String ,String >();
	
	@Override
	public void saveAuthorizationCode(String code) {
		cache.put(code,code);
	}
	
	@Override
	public String getAuthorzationCode(String code) {
		return cache.remove(code);
	}

	@Override
	public ClientInfo getClientInfo(String clientId) {
		return clientInfoRepository.getByClientId(clientId);
	}

	@Override
	public boolean checkClientSecret(String clientId, String clientSecret) {
		ClientInfo clientInfo=clientInfoRepository.getByClientId(clientId);
		return clientInfo!=null && clientInfo.getClientSecret().equals(clientSecret);
		 
	}

	@Override
	public boolean checkClientId(String clientId) {
		return clientInfoRepository.getByClientId(clientId)!=null;
	}

	@Override
	public boolean checkRedirectUrl(String clientId,String redirectUrl) {
		ClientInfo clientInfo=clientInfoRepository.getByClientId(clientId);
		return clientInfo!=null && clientInfo.getRedirectUrl().equals(redirectUrl);
	}

	@Override
	public String getAccessToken(String clientId, String username) {
		AccessTokenInfo token = accessTokenRepository.getByClientIdAndUsername(clientId, username);
		if(token !=null){
			return token.getAccessToken();
		}
		return null;
	}

	@Override
	public void saveAccessToken(String accessToken,String refreshToken, String clientId,
			String username, Date expireIn) {
		accessTokenRepository.save(new AccessTokenInfo(accessToken,refreshToken,expireIn,clientId,username));
	}


}
