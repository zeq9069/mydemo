package com.demo.NettyDemo.example.twelve;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class OkResponseHandler extends SimpleChannelInboundHandler<SocketChannel>{

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, SocketChannel msg)
			throws Exception {
		final FullHttpResponse response= new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		response.headers().set("custom-response-header", "Some value");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

}
