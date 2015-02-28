package com.demo.NettyDemo.exmple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 
 * @author lenovo
 *
 */
public class EchoClient {

	private final int message;

	public EchoClient(int message) {
		this.message = message;
	}

	public void run() throws InterruptedException {
		NioEventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.TCP_NODELAY, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new EchoClientHandler(message));
				}
			});

			ChannelFuture f = b.connect("localhost", 9090).sync();
			f.channel().closeFuture().sync();

		} finally {
			group.shutdownGracefully();
		}
	}

	public static void main(String s[]) {
		new EchoClient(256);
	}

}
