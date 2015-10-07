package com.demo.NettyDemo.example.fifteen;

import java.util.Timer;
import java.util.TimerTask;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class HttpServerHandler2 extends ChannelHandlerAdapter{

	String res="<a href=''>Hello , I'm kyrin !</a>";
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if(msg instanceof FullHttpRequest){
			FullHttpRequest request=(FullHttpRequest)msg;
			System.out.println("FullHttpRequest:"+request.content().readableBytes());
			final FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,Unpooled.copiedBuffer(res.getBytes()));
			response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH,response.content().readableBytes());
			response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/html; charset=utf-8");
			response.headers().set(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
			ctx.write(response);
			final Channel c=ctx.channel();
			Timer t=new Timer();
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					c.writeAndFlush(response);
				}
			}, 1000);
		}
		
		
		
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
}
