package com.demo.NettyDemo.example.eighteen;

import java.io.Serializable;

public class MyResponse implements Serializable {

	private static final long serialVersionUID = 6026890375566318189L;

	private final byte type = (byte) 1;

	private String message;

	public MyResponse() {
		// TODO Auto-generated constructor stub
	}

	public MyResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public byte getType() {
		return type;
	}

}
