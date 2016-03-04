package com.kyrin.OltuDemo.respository;

import org.springframework.data.repository.Repository;

import com.kyrin.OltuDemo.model.ClientInfo;

/**
 * cleintInfo 持久层
 * @author kyrin
 *
 */
public interface ClientInfoRespository extends Repository<ClientInfo,Integer> {
	
	
	public ClientInfo getByClientId(String clientId);
	

}
