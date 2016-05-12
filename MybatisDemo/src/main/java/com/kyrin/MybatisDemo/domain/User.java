package com.kyrin.MybatisDemo.domain;

/**
 *  用户实体
 * @author kyrin
 *
 */
public class User {

	private int id;
	
	private String username;
	
	private String email;
	
	private String password;
	
	public User() {
	}
	
	public User(String username,String email,String password){
		this.username=username;
		this.email=email;
		this.password=password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString(){
		return String.format("[id=%d,username=%s , email=%s ]", id,username,email);
	}
	
}
