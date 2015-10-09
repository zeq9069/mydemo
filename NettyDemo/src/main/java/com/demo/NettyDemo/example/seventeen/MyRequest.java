package com.demo.NettyDemo.example.seventeen;

public class MyRequest {

	private final byte type = (byte) 0;

	private String message;

	public MyRequest() {
		// TODO Auto-generated constructor stub
	}

	public MyRequest(String message) {
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
