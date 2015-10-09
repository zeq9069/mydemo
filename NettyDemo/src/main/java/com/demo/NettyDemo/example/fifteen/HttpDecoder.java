package com.demo.NettyDemo.example.fifteen;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;

public class HttpDecoder extends MessageToMessageDecoder<FullHttpRequest> {

	@Override
	protected void decode(ChannelHandlerContext ctx, FullHttpRequest msg, List<Object> out) throws Exception {

	}
}
