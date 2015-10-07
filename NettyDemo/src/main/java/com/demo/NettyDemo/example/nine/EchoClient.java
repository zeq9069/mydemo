package com.demo.NettyDemo.example.nine;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
	
	private static String host="127.0.0.1";
	private static int port=8888;
	
	public static void main(String[] args) {
		EventLoopGroup c=new NioEventLoopGroup();
		Bootstrap client=new Bootstrap();
		client.group(c);
		client.channel(NioSocketChannel.class);
		client.option(ChannelOption.TCP_NODELAY, true);
		client.handler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				ch.pipeline().addLast(new MyClientHandler("Helo Netty!"));
			}
		});
		
		try {
			ChannelFuture cf=client.connect(host,port).sync();
			cf.channel().closeFuture().sync();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			client.register().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			c.shutdownGracefully();
		}
	}

}
