package com.demo.NettyDemo.example.fourteen;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class HttpHelloWorldServer {
	
	public static void main(String[] args) {
		EventLoopGroup boss=new NioEventLoopGroup();
		EventLoopGroup work=new NioEventLoopGroup();
		
		ServerBootstrap server=new ServerBootstrap();
		server.group(boss, work);
		server.channel(NioServerSocketChannel.class);
		server.option(ChannelOption.SO_BACKLOG, 1024);
		server.handler(new LoggingHandler(LogLevel.INFO));
		server.childHandler(new HttpHelloWorldChannelInitailizer());
		
		try {
			ChannelFuture cf = server.bind(8080).sync();
			cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			boss.shutdownGracefully();
			work.shutdownGracefully();
		}
		
	}

}
