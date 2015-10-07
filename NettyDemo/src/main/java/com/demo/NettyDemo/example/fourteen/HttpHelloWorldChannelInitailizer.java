package com.demo.NettyDemo.example.fourteen;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpHelloWorldChannelInitailizer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pip=ch.pipeline();
		pip.addLast(new HttpServerCodec());
		pip.addLast(new HttpHelloWorldHandler());
	}

}
