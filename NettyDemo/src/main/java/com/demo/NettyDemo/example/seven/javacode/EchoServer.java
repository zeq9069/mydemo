package com.demo.NettyDemo.example.seven.javacode;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * ***********************
 *   netty 练习
 *   
 *   java序列化编码、解码
 * 
 *  
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年3月30日]
 *
 */
public class EchoServer {
	
	public void start(int port){
		NioEventLoopGroup bossGroup=new NioEventLoopGroup();
		NioEventLoopGroup workerGroup=new NioEventLoopGroup();
		ServerBootstrap server=new ServerBootstrap();
		server.group(bossGroup,workerGroup);
		server.channel(NioServerSocketChannel.class);
		server.option(ChannelOption.SO_BACKLOG, 100);
		server.handler(new LoggingHandler(LogLevel.INFO));
		server.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new ObjectDecoder(1024*1024, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
				ch.pipeline().addLast(new ObjectEncoder());
				ch.pipeline().addLast(new EchoServerHandler());
			}
		});
		try {
			ChannelFuture f=server.bind(port).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String a[]){
		new EchoServer().start(9999);
	}
	
}
