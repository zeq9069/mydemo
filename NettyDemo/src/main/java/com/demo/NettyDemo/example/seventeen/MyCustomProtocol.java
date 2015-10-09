package com.demo.NettyDemo.example.seventeen;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

/**
 * 自定义简单的二进制通信协议，因为二进制是最常用的
 * 
 * 协议数据包：(header+body) ByteBuf
 * 
 *   |       header     | body| 
 * 	 |	1B  |   4B      |
 *   --------------------------
 *   | Type | bodyLenght| body|
 *   --------------------------
 *   
 *   Type:(1字节) request  or  response 
 *   bodyLength:(整形，4字节) 消息的大小长度  
 *   
 * 
 * ByteBuf 支持的类型以及转换为字节的大小：
 * 
 * [类型]                   [字节]
 * ++++++++++++++++++++++++++++
 * Char                      2
 * Byte                      1(8位)
 * Double                    8
 * float                     4
 * long                      8
 * int                       4
 * short                     2(16位)
 * boolean                   1
 * ++++++++++++++++++++++++++++
 * 
 * @author kyrin
 *
 */
public class MyCustomProtocol {

	public static ByteBuf encode(Object obj, ByteBuf buf) {
		byte type = -1;
		String message = "";
		if (obj instanceof MyRequest) {
			MyRequest request = (MyRequest) obj;
			type = request.getType();
			message = request.getMessage();
		} else if (obj instanceof MyResponse) {
			MyResponse response = (MyResponse) obj;
			type = response.getType();
			message = response.getMessage();
		}
		byte[] bs = message.getBytes();
		buf.writeByte(type);
		buf.writeInt(bs.length);
		buf.writeBytes(bs);
		return buf;
	}

	public static Object decode(ByteBuf in) {
		if (in.readableBytes() < 5) {
			return null;
		}
		byte type = in.readByte();
		if (type == 0) {
			MyRequest request = new MyRequest();
			int messageLength = in.readInt();
			ByteBuf message = in.readBytes(messageLength);
			request.setMessage(message.toString(Charset.defaultCharset()));
			return request;
		} else if (type == 1) {
			MyResponse response = new MyResponse();
			int messageLength = in.readInt();
			ByteBuf message = in.readBytes(messageLength);
			response.setMessage(message.toString(Charset.defaultCharset()));
			return response;
		}
		return null;
	}
}
