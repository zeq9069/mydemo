package com.kyrincloud.RPCDemo.message;
/**
 * client端请求
 * @author kyrin
 *
 */
public class Request {
	
	public static final byte PROTOCOL=(byte)1;
	
	public static final byte TYPE=(byte)1;
	
	private String targetInterfaceName;
	
	private String methodName;
	
	private byte[][] argsType;
	
	private byte[][] args;
	
	
	public Request() {
	}
	
	public Request(String targetInterfaceName,String methodName,byte[][] args,byte[][] argsType){
		this.targetInterfaceName = targetInterfaceName;
		this.methodName = methodName;
		this.args = args;
		this.argsType = argsType;
	}

	public String getTargetInterfaceName() {
		return targetInterfaceName;
	}

	public void setTargetInterfaceName(String targetInterfaceName) {
		this.targetInterfaceName = targetInterfaceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public byte[][] getArgsType() {
		return argsType;
	}

	public void setArgsType(byte[][] argsType) {
		this.argsType = argsType;
	}

	public byte[][] getArgs() {
		return args;
	}

	public void setArgs(byte[][] args) {
		this.args = args;
	}

}
