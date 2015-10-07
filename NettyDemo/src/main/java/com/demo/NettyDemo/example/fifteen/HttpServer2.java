package com.demo.NettyDemo.example.fifteen;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class HttpServer2 {

	public static void main(String[] args) {
		EventLoopGroup boss=new NioEventLoopGroup();
		EventLoopGroup work=new NioEventLoopGroup();
		
		ServerBootstrap server=new ServerBootstrap();
		server.group(boss,work);
		server.channel(NioServerSocketChannel.class);
		server.handler(new LoggingHandler(LogLevel.INFO));
		server.option(ChannelOption.SO_KEEPALIVE, true);
		server.childHandler(new HttpChannelInitializer2());
		try {
			ChannelFuture cf=server.bind(8080).sync();
			cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			boss.shutdownGracefully();
			work.shutdownGracefully();
		}
	}
}
