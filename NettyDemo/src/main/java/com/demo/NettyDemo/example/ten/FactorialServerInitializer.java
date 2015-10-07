package com.demo.NettyDemo.example.ten;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;

public class FactorialServerInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline pipline=ch.pipeline();
		pipline.addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
		pipline.addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
		
		pipline.addLast(new BigIntegerDecoder());
		pipline.addLast(new NumberEncoder());
		
		pipline.addLast(new FactorialServerHandler());
	}

}
