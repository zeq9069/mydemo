package com.demo.NettyDemo.example.sixteen;

import java.io.Serializable;

public class MyResponse implements Serializable {

	private static final long serialVersionUID = -4695844400026596389L;

	private int requestId = 1;

	public MyResponse(int requestId) {
		this.requestId = requestId;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

}
