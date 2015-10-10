package com.demo.NettyDemo.example.nineteen;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 
 * @author kyrin
 *
 */
public class TcpServer {

	public static void main(String[] args) {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup work = new NioEventLoopGroup();

		ServerBootstrap server = new ServerBootstrap();
		server.group(boss, work);
		server.channel(NioServerSocketChannel.class);
		server.childHandler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pip = ch.pipeline();
				pip.addLast(new KryoDecoder());
				pip.addLast(new KryoEncoder());
				pip.addLast(new MyServerHandler());
			}
		});

		try {
			ChannelFuture cf = server.bind(9999).sync();
			cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			boss.shutdownGracefully();
			work.shutdownGracefully();
		}
	}
}
