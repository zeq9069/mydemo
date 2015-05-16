package com.demo.springSecurity.domain;

import java.io.Serializable;

public class Resource implements Serializable{
	
	private static final long serialVersionUID = -3211990361158504972L;
	private int resourceID;
	private String resourceName;
	private String resourceURL;
	public int getResourceID() {
		return resourceID;
	}
	public void setResourceID(int resourceID) {
		this.resourceID = resourceID;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getResourceURL() {
		return resourceURL;
	}
	public void setResourceURL(String resourceURL) {
		this.resourceURL = resourceURL;
	}
	

}
