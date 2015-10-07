package com.demo.NettyDemo.example.eleven;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

/**
 * Server that accept the path of a file an echo back its content.
 * eg.
 * >telnet localhost 8023
 * >/root/www.txt
 * 
 */
public class FileServer {

	      // Use the same default port with the telnet example so that we can use the telnet client example to access it.
	      static final int PORT = Integer.parseInt(System.getProperty("port", "8023"));
	  
	      public static void main(String[] args) throws Exception {
	  
	          // Configure the server.
	          EventLoopGroup bossGroup = new NioEventLoopGroup(1);
	          EventLoopGroup workerGroup = new NioEventLoopGroup();
	          try {
	              ServerBootstrap b = new ServerBootstrap();
	              b.group(bossGroup, workerGroup)
	               .channel(NioServerSocketChannel.class)
	               .option(ChannelOption.SO_BACKLOG, 100)
	               .handler(new LoggingHandler(LogLevel.INFO))
	               .childHandler(new ChannelInitializer<SocketChannel>() {
	                    public void initChannel(SocketChannel ch) throws Exception {
	                        ChannelPipeline p = ch.pipeline();
	                        p.addLast(
	                                new StringEncoder(CharsetUtil.UTF_8),
	                                new LineBasedFrameDecoder(8192),
	                                new StringDecoder(CharsetUtil.UTF_8),
	                                new ChunkedWriteHandler(),
	                                new FileServerHandler());
	                    }
	                });
	   
	               // Start the server.
	               ChannelFuture f = b.bind(PORT).sync();
	   
	               // Wait until the server socket is closed.
	               f.channel().closeFuture().sync();
	           } finally {
	               // Shut down all event loops to terminate all threads.
	               bossGroup.shutdownGracefully();
	               workerGroup.shutdownGracefully();
	           }
	       }
	
}
