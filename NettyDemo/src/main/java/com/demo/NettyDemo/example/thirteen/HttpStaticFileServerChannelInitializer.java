package com.demo.NettyDemo.example.thirteen;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpStaticFileServerChannelInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pip=ch.pipeline();
		pip.addLast(new HttpServerCodec());
		pip.addLast(new HttpObjectAggregator(65536));
		pip.addLast(new ChunkedWriteHandler());
		pip.addLast(new HttpStaticFileServerHandler());
	}
}
