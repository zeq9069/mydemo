package com.demo.NettyDemo.example.nineteen;


/**
 * hession 要求java对象必须实现序列化接口
 * @author kyrin
 *
 */
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
