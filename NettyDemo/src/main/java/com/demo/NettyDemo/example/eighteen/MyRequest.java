package com.demo.NettyDemo.example.eighteen;

import java.io.Serializable;

/**
 * hession 要求java对象必须实现序列化接口
 * @author kyrin
 *
 */
public class MyRequest implements Serializable {

	private static final long serialVersionUID = 95153675312082112L;

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
