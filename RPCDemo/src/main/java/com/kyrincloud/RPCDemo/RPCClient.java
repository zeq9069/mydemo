package com.kyrincloud.RPCDemo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import com.kyrincloud.RPCDemo.codec.MyCodec;

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
	private static int port=9999;
	
	public static Object invokeResult(String instanceName,String methodName,String[] argsType,byte[][] args) throws IOException{
		ss=new Socket(host,port);
		os=ss.getOutputStream();
		is=ss.getInputStream();
		sendRequest(os, instanceName, methodName, argsType, args);
		Object re=null;
		re= getResult(is);
		is.close();
		os.close();
		return re;
	}
	
	private static void sendRequest(OutputStream os,String instanceName,String methodName,String[] _argsType,byte[][] _args) throws IOException{
		byte [] _clazz=instanceName.getBytes();
		byte [] _method=methodName.getBytes();
		int allArgsLength=0;
		for(int i=0;i<_args.length;i++){
			allArgsLength+=_args[i].length;
		}
		
		int allArgsTypeLen=0;
		for(int i=0;i<_argsType.length;i++){
			allArgsLength+=_argsType[i].getBytes().length;
		}
		
		
		ByteBuffer bb=ByteBuffer.allocate(14+_clazz.length+_method.length+4+_args.length*4*2+4+allArgsLength+allArgsTypeLen);
		bb.put((byte)1);
		bb.put((byte)1);
		bb.putInt(_clazz.length);
		bb.putInt(_method.length);
		bb.putInt(_args.length);
		for(int i=0;i<_args.length;i++){
			bb.putInt(_args[i].length);
		}
		bb.putInt(_args.length);
		for(int i=0;i<_argsType.length;i++){
			bb.putInt(_argsType[i].getBytes().length);
		}
		
		
		bb.put(instanceName.getBytes());
		bb.put(methodName.getBytes());
		
		for(int i=0;i<_args.length;i++){
			bb.put(_args[i]);
		}
		for(int i=0;i<_argsType.length;i++){
			bb.put(_argsType[i].getBytes());
		}
		
		os.write(bb.array());
		os.flush();
	}
	
	//header+body= version(1byte)+消息类型（1byte）+body_len(4byte)+body
	private static Object getResult(InputStream is) throws IOException{
		byte[] h=new byte[2];
		is.read(h);
		if(h[0]==(byte)1 && h[1]==(byte)0){
			ByteBuffer b_len=ByteBuffer.allocate(4);
			is.read(b_len.array());
			int body_len=b_len.getInt();
			byte[] body=new byte[body_len];
			is.read(body);
			return MyCodec.decode(body);
		}
		return null;
	}
	
}
