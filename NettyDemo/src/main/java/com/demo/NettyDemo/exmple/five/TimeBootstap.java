package com.demo.NettyDemo.exmple.five;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * ***********************************
 *  netty练习
 *  
 *  时间服务启动类
 *  
 *  利用LineBaseFrameDecoder解决 TCP粘包
 *
 * ***********************************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年3月28日]
 *
 */
public class TimeBootstap {
	
	public  void startService(int port){
		EventLoopGroup bossGroup=new  NioEventLoopGroup();
		EventLoopGroup workerGroup=new NioEventLoopGroup();
		
		ServerBootstrap server=new ServerBootstrap();
		server.group(bossGroup, workerGroup);
		server.channel(NioServerSocketChannel.class);
		server.option(ChannelOption.SO_BACKLOG, 1024);
		server.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
				ch.pipeline().addLast(new StringDecoder());
				ch.pipeline().addLast(new TimeServerHandler());
			}
		});
		
		try {
			ChannelFuture cf=server.bind(port).sync();
			cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	public static void main(String srgs[]){
		int port=9090;
		new TimeBootstap().startService(port);
	}
}
