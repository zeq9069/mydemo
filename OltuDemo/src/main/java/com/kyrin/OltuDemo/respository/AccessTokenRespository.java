package com.kyrin.OltuDemo.respository;

import org.springframework.data.repository.Repository;

import com.kyrin.OltuDemo.model.AccessTokenInfo;

public interface AccessTokenRespository extends Repository<AccessTokenInfo,Integer> {

	public void save(AccessTokenInfo accessTokenInfo);
	
	public AccessTokenInfo getByClientIdAndUsername(String clientId,String username);
	
}
