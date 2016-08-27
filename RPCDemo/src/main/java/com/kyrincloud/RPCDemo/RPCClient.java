package com.kyrincloud.RPCDemo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.kyrincloud.RPCDemo.codec.MyCodec;
import com.kyrincloud.RPCDemo.message.Request;
import com.kyrincloud.RPCDemo.message.Response;
import com.kyrincloud.RPCDemo.protocol.factory.RPCProtocolFactory;

/**
 * 通信协议： header+body = 协议（1byte）+ 消息类型（1byte）+接口类长度（4byte）+方法名长度（4byte）+方法参数个数（4byte）+方法参数个数*4byte +方法参数类型个数（4byte）+方法参数类型个数*4byte+body内容
 * 注意：参数类型的个数和参数的个数是一样的
 * @author kyrin
 *
 */
public class RPCClient {
	
	private static Socket ss;
	private static InputStream is;
	private static OutputStream os;
	private static String host="127.0.0.1";
	private static int port=8888;
	
	static{
		try {
			ss=new Socket(host,port);
			os=ss.getOutputStream();
			is=ss.getInputStream();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static Object invokeResult(Request request) throws IOException{
		sendRequest(os,request);
		Object re=null;
		re= getResult(is);
		return re;
	}
	
	private static void sendRequest(OutputStream os,Request request) throws IOException{
		os.write(RPCProtocolFactory.getProtocol(Request.PROTOCOL).encode(request));
		os.flush();
	}
	
	//header+body= version(1byte)+消息类型（1byte）+body_len(4byte)+body
	private static Object getResult(InputStream is) throws IOException{
		Response resp= (Response) RPCProtocolFactory.getProtocol(Response.PROTOCOL).decode(is);
		return MyCodec.decode(resp.getResult());
	}
	
}
