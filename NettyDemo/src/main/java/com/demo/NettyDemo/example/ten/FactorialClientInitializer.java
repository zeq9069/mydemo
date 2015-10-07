package com.demo.NettyDemo.example.ten;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;

public class FactorialClientInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		 ChannelPipeline pipeline = ch.pipeline();
  
          // Enable stream compression (you can remove these two if unnecessary)
          pipeline.addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
          pipeline.addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
  
          // Add the number codec first,
          pipeline.addLast(new BigIntegerDecoder());
          pipeline.addLast(new NumberEncoder());
  
          // and then business logic.
          pipeline.addLast(new FactorialClientHandler());
	}

}
