package com.demo.NettyDemo.example.fifteen;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpChannelInitializer1 extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipline=ch.pipeline();
		/**	new HttpServerCodec()效果等同于一下两个：
		 * pipline.addLast(new HttpRequestDecoder());
		 * pipline.addLast(new HttpResponseEncoder());
		 */
		pipline.addLast(new HttpServerCodec());
		pipline.addLast(new HttpServerHandler1());
	}

}
