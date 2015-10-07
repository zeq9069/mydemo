package com.demo.NettyDemo.example.fourteen;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

   
public class HttpHelloWorldHandler extends ChannelHandlerAdapter{
      private static final byte[] CONTENT = { 'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd' };

	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
		
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if(msg instanceof HttpRequest){
		HttpRequest request=(HttpRequest)msg;
		if(HttpHeaderUtil.is100ContinueExpected(request)){
			ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE));
		}
		
		boolean keepAlive=HttpHeaderUtil.isKeepAlive(request);
		FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,Unpooled.wrappedBuffer(CONTENT));
		response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
		response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH,response.content().readableBytes());
		response.headers().set(HttpHeaderNames.CACHE_CONTROL,HttpHeaderValues.PRIVATE);
		if(!keepAlive){
			ctx.write(response).addListener(ChannelFutureListener.CLOSE);
		}else{
			response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
			ctx.write(response);
		}
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}


}
