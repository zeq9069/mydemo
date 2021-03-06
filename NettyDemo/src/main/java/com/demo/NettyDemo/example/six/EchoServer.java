package com.demo.NettyDemo.example.six;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * *******************************
 *  netty 练习
 * 
 * 利用
 *  （1）DelimiterBaseFrameDecoder 
 *  （2）FixedLengthFrameDecoder
 *  
 *  来解决 粘包/拆包 带来的读半包的问题
 *
 * ******************************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年3月29日]
 *
 */
public class EchoServer {
	
	public void start(int port){
		
		NioEventLoopGroup bossGroup=new NioEventLoopGroup();
		NioEventLoopGroup workerGroup=new NioEventLoopGroup();
		ServerBootstrap server=new ServerBootstrap();
		server.group(bossGroup, workerGroup);
		server.channel(NioServerSocketChannel.class);
		server.option(ChannelOption.SO_BACKLOG,1024);
		server.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				/*  分隔符
				 ByteBuf delimiter=Unpooled.copiedBuffer("$_".getBytes());
				 ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
				*/
				ch.pipeline().addLast(new FixedLengthFrameDecoder(5));//每次只读取5字节,如果读的是半包关系，会等待下一次请求拼接成一个完整包
				ch.pipeline().addLast(new StringDecoder());
				ch.pipeline().addLast(new EchoServerHandler());
			}
		});
		
		
		try {
			ChannelFuture future= server.bind(port).sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		
	}
	public static void main(String s[]){
		new EchoServer().start(9090);
	}
}
