package com.demo.NettyDemo.example.sixteen;

import java.io.Serializable;

public class MyRequest implements Serializable {

	private static final long serialVersionUID = 14152631038586341L;

	private int id = 1;

	public MyRequest() {
		this(1);
	}

	public MyRequest(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
