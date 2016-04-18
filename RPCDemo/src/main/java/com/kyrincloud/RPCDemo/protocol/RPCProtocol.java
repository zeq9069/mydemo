package com.kyrincloud.RPCDemo.protocol;

import java.io.IOException;
import java.io.InputStream;

/**
 * 通信协议接口
 * @author kyrin
 *
 */
public interface RPCProtocol {

	
	public byte[] encode(Object message);
	
	public Object decode(InputStream is)throws IOException ;
	
	
}
