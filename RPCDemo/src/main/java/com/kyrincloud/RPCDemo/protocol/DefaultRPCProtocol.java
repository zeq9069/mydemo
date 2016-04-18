package com.kyrincloud.RPCDemo.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.kyrincloud.RPCDemo.message.Request;
import com.kyrincloud.RPCDemo.message.Response;

/**
 * 默认是的rpc通信协议实现
 * @author kyrin
 *
 */
public class DefaultRPCProtocol implements RPCProtocol{

	public byte[] encode(Object message) {

		if(message instanceof Request){
			Request req=(Request)message; 
			byte [] _clazz=req.getTargetInterfaceName().getBytes();
			byte [] _method=req.getMethodName().getBytes();
			byte[][] _args=req.getArgs();
			byte[][] _argsType=req.getArgsType();
			int allArgsLength=0;
			for(int i=0;i<_args.length;i++){
				allArgsLength+=_args[i].length;
			}
			
			int allArgsTypeLen=0;
			for(int i=0;i<_argsType.length;i++){
				allArgsLength+=_argsType[i].length;
			}
			
			
			ByteBuffer bb=ByteBuffer.allocate(14+_clazz.length+_method.length+4+_args.length*4*2+4+allArgsLength+allArgsTypeLen);
			bb.put(req.PROTOCOL);
			bb.put(req.TYPE);
			bb.putInt(_clazz.length);
			bb.putInt(_method.length);
			bb.putInt(_args.length);
			for(int i=0;i<_args.length;i++){
				bb.putInt(_args[i].length);
			}
			bb.putInt(_args.length);
			for(int i=0;i<_argsType.length;i++){
				bb.putInt(_argsType[i].length);
			}
			
			
			bb.put(_clazz);
			bb.put(_method);
			
			for(int i=0;i<_args.length;i++){
				bb.put(_args[i]);
			}
			for(int i=0;i<_argsType.length;i++){
				bb.put(_argsType[i]);
			}
			return bb.array();
		}else if(message instanceof Response){
			Response resp=(Response) message;
			byte[] res=resp.getResult();
			ByteBuffer bb=ByteBuffer.allocate(6+res.length);
			bb.put(resp.PROTOCOL);
			bb.put(resp.TYPE);
			bb.putInt(res.length);
			bb.put(res);
			return bb.array();
		}else{
			throw new IllegalArgumentException("The message must is Rquest or Response");
		}
	}

	public Object decode(InputStream is) throws IOException {

		byte[] h = new byte[2];
		is.read(h);
		if (h[0] !=  1) {
			throw new IllegalArgumentException(
					"The message package protocol is error");
		}
		if (h[1] != 1 && h[1] != 0) {
			throw new IllegalArgumentException(
					"The message package type is error");
		}
		if (h[1] ==1) {
			Request request = new Request();
			ByteBuffer buff = ByteBuffer.allocate(4);
			int clazz_len = readInt(is, buff);
			int method_len = readInt(is, buff);
			int argsNum = readInt(is, buff);

			List<Integer> allArgsLen = new ArrayList<Integer>();
			List<Integer> allArgsTypeLen = new ArrayList<Integer>();
			for (int i = 1; i <= argsNum; i++) {
				allArgsLen.add(readInt(is, buff));
			}
			is.skip(4);
			for (int i = 1; i <= argsNum; i++) {
				allArgsTypeLen.add(readInt(is, buff));
			}

			byte[] _clazz = new byte[clazz_len];
			byte[] _method = new byte[method_len];
			is.read(_clazz);
			is.read(_method);
			byte[][] args = new byte[allArgsLen.size()][];
			byte[][] argsType = new byte[allArgsTypeLen.size()][];
			for (int i = 0; i < argsNum; i++) {
				byte[] b = new byte[allArgsLen.get(i)];
				is.read(b);
				args[i] = b;
			}
			for (int i = 0; i < argsNum; i++) {
				byte[] b = new byte[allArgsTypeLen.get(i)];
				is.read(b);
				argsType[i] = b;
			}
			request.setTargetInterfaceName(new String(_clazz));
			request.setMethodName(new String(_method));
			request.setArgs(args);
			request.setArgsType(argsType);
			return request;
		} else if (h[1] == (byte) 0) {
			Response response = new Response();
			ByteBuffer b_len = ByteBuffer.allocate(4);
			is.read(b_len.array());
			int body_len = b_len.getInt();
			byte[] body = new byte[body_len];
			is.read(body);
			response.setResult(body);
			return response;
		}

		return null;
	}
		
		private static int readInt(InputStream is,ByteBuffer buff) throws IOException{
			byte[] clazz=buff.array();
			is.read(clazz);
			int result= buff.getInt();
			buff.clear();
			return result;
		}

}
