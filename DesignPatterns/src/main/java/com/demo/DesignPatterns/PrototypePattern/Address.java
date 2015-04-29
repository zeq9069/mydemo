package com.demo.DesignPatterns.PrototypePattern;

import java.io.Serializable;

public class Address implements Serializable{
	private static final long serialVersionUID = -263803120959909478L;
	
	String city;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
