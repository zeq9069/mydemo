package com.demo.NettyDemo.example.seventeen;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 
 * @author lenovo
 *
 */
public class MyCustomDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		Object obj = MyCustomProtocol.decode(in);
		out.add(obj);
	}
}
