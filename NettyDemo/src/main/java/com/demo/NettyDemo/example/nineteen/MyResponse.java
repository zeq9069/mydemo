package com.demo.NettyDemo.example.nineteen;

public class MyResponse {

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
