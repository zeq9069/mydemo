package com.demo.SpringBootDemo.resource;

import org.springframework.hateoas.Identifiable;


public class Users implements Identifiable<Long> {
	
	private Long id;
	
	private String name;
	
	private String email;
	
	public Users(Long id,String name,String email) {
		this.id=id;
		this.name=name;
		this.email=email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
