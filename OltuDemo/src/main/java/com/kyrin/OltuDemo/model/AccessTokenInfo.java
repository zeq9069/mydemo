package com.kyrin.OltuDemo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="access_token")
public class AccessTokenInfo implements Serializable{
	
	private static final long serialVersionUID = -7438396492966845165L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="access_token",length=64)
	private String accessToken;
	
	@Column(name="refresh_token",length=64)
	private String refreshToken;
	
	@Column(name="expire_in",updatable=false)
	private Date expireIn;
	
	@Column(name="client_id",length=64)
	private String clientId;
	
	@Column(name="username",length=64)
	private String username;
	
	
	
	public AccessTokenInfo() {
	}
	
	public AccessTokenInfo(String accessToken ,String refreshToken,Date expireIn,String clientId,String username){
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expireIn = expireIn;
		this.clientId = clientId;
		this.username = username;
	}
	
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Date getExpireIn() {
		return expireIn;
	}

	public void setExpireIn(Date expireIn) {
		this.expireIn = expireIn;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String toString(){
		return String.format("[clientId = %s , username = %s , accessToken = %s , refreshToken = %s]", clientId,username,accessToken,refreshToken);
	}
	
}
