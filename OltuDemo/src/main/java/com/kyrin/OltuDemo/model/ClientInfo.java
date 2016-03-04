package com.kyrin.OltuDemo.model;

import groovy.transform.stc.ClosureParams;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * client 信息
 * @author kyrin
 *
 */
@Entity
@Table(name="client_info")
public class ClientInfo implements  Serializable                                                                                                                                  {

	private static final long serialVersionUID = 392266296880043089L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="client_name",length=64)
	private String clientName;
	
	
	@Column(name="client_id",length=64)
	private String clientId;
	
	@Column(name="client_secret",length=64)
	private String clientSecret;
	
	@Column(name="redirect_url",length=64)
	private String redirectUrl;

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	
	public String toString(){
		return String.format("[clientName = %s , clientId = %s , clientSecret = %s , redirectUrl = %s]", clientName,clientId,clientSecret,redirectUrl);
	}
	
}
