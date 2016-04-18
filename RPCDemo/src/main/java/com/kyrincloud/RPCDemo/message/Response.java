package com.kyrincloud.RPCDemo.message;

/**
 * server response 
 * @author kyrin
 *
 */
public class Response {


	public static final byte PROTOCOL=(byte)1;
	
	public static final byte TYPE=(byte)0;
	
	private byte[] result;

	
	public Response() {
	}
	
	public Response(byte[] result) {
		this.result = result;
	}


	public byte[] getResult() {
		return result;
	}


	public void setResult(byte[] result) {
		this.result = result;
	}
	 
}
