package com.demo.NettyDemo.example.ten;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class FactorialClient {

	static final String HOST = System.getProperty("host", "127.0.0.1");
	static final int PORT = Integer
			.parseInt(System.getProperty("port", "8888"));
	static final int COUNT = Integer.parseInt(System.getProperty("count",
			"1000"));

	public static void main(String[] args) {
		EventLoopGroup c = new NioEventLoopGroup();

		Bootstrap client = new Bootstrap();
		client.group(c);
		client.channel(NioSocketChannel.class);

		client.handler(new FactorialServerInitializer());

		try {
			// Make a new connection.
			ChannelFuture f = client.connect(HOST, PORT).sync();
			
			// Get the handler instance to retrieve the answer.
			FactorialClientHandler handler = (FactorialClientHandler) f
					.channel().pipeline().last();

			// Print out the answer.
			System.err.format("Factorial of %,d is: %,d", COUNT,
					handler.getFactorial());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			c.shutdownGracefully();
		}

	}

}
