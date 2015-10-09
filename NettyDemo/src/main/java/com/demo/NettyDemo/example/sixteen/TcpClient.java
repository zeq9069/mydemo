package com.demo.NettyDemo.example.sixteen;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TcpClient {

	public static void main(String[] args) {
		EventLoopGroup work = new NioEventLoopGroup();

		Bootstrap b = new Bootstrap();
		b.group(work);
		b.channel(NioSocketChannel.class);
		b.handler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipline = ch.pipeline();
				pipline.addLast(new MyJavaDecoder());
				pipline.addLast(new MyJavaEncoder());
				pipline.addLast(new MyClientHandler());
			}
		});
		try {
			ChannelFuture cf = b.connect("127.0.0.1", 9999).sync();
			cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			work.shutdownGracefully();
		}
	}
}
