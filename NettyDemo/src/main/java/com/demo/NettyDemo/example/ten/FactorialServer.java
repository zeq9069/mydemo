package com.demo.NettyDemo.example.ten;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 
 * @author kyrin
 *
 */
public class FactorialServer {
	
	public static void main(String[] args) {
		EventLoopGroup boss=new NioEventLoopGroup(); 
		EventLoopGroup work=new NioEventLoopGroup();
		
		ServerBootstrap server=new ServerBootstrap();
		server.group(boss, work);
		server.channel(NioServerSocketChannel.class);
		server.option(ChannelOption.SO_KEEPALIVE, true);
		server.handler(new LoggingHandler(LogLevel.INFO));
		server.childHandler(new FactorialServerInitializer());
		try {
			server.bind(8888).channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			boss.shutdownGracefully();
			work.shutdownGracefully();
		}
	}
}
