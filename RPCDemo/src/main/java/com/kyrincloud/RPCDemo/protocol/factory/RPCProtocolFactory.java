package com.kyrincloud.RPCDemo.protocol.factory;

import com.kyrincloud.RPCDemo.protocol.DefaultRPCProtocol;
import com.kyrincloud.RPCDemo.protocol.RPCProtocol;

/**
 * 协议工厂类
 * @author kyrin
 *
 */
public class RPCProtocolFactory {

	private static RPCProtocol[] rpcProtocols=new RPCProtocol[2];
	
	static{
		registerProtocol(1,new DefaultRPCProtocol());
	}
	
	private static void registerProtocol(int protocolType,RPCProtocol rpcProtocol){
		if(protocolType>rpcProtocols.length){
			 RPCProtocol[] dstProtocols=new RPCProtocol[protocolType+1];
			 System.arraycopy(rpcProtocols, 0, dstProtocols, 0, dstProtocols.length);
			 rpcProtocols=dstProtocols;
		}
		rpcProtocols[protocolType]=rpcProtocol;
	}
	
	public static RPCProtocol getProtocol(int protocolType){
		return rpcProtocols[protocolType];
	}

}
