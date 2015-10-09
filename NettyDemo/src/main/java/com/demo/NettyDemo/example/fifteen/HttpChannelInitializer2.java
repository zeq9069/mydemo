package com.demo.NettyDemo.example.fifteen;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpChannelInitializer2 extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipline = ch.pipeline();
		pipline.addLast(new HttpRequestDecoder());
		pipline.addLast(new HttpResponseEncoder());
		pipline.addLast(new HttpObjectAggregator(1048576));//聚合httpMessage和HttpContent为FullHttpRquest或者FullHttpResponse
		pipline.addLast(new ChunkedWriteHandler());// adds support for writing a large data stream asynchronously neither spending a lot of memory nor getting OutOfMemoryError
		pipline.addLast(new HttpServerHandler2());
	}
}
