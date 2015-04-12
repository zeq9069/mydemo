package com.demo.NettyDemo.example.six;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
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
public class EchoClient {
	private  static final String DELIMITER="$_";
	private static final String MESSAGE="Hello,World!";
	public void connect(String host,int port){
		NioEventLoopGroup bossGroup=new NioEventLoopGroup();
		Bootstrap client=new Bootstrap();
		client.group(bossGroup);
		client.channel(NioSocketChannel.class);
		client.option(ChannelOption.TCP_NODELAY, true);
		client.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				/*
				 * 分隔符
				ByteBuf delimiter=Unpooled.copiedBuffer(DELIMITER.getBytes());
				ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
				*/
				ch.pipeline().addLast(new FixedLengthFrameDecoder(5));
				ch.pipeline().addLast(new StringDecoder());
				ch.pipeline().addLast(new EchoClienHandler(MESSAGE, DELIMITER));
			}
		});
		try {
			ChannelFuture f=client.connect(host,port).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			bossGroup.shutdownGracefully();
		}
	}
	public static void main(String a[]){
		String host="127.0.0.1";
		int port=9090;
		new EchoClient().connect(host, port);
	}

}
