package com.demo.NettyDemo.example.seven.javacode;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * ***********************
 *   netty 练习
 *   
 *   java序列化编解码
 *   
 *   客户端
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年3月30日]
 *
 */
public class EchoClient {
	
	public void connet(String host,int port){
		NioEventLoopGroup boss=new NioEventLoopGroup();
		Bootstrap client=new Bootstrap();
		client.group(boss);
		client.channel(NioSocketChannel.class);
		client.option(ChannelOption.TCP_NODELAY, true);
		client.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new ObjectDecoder(1024*1024,ClassResolvers.
						weakCachingConcurrentResolver(this.getClass().getClassLoader())));
				ch.pipeline().addLast(new ObjectEncoder());
				ch.pipeline().addLast(new EchoClientHandler());
			}
		});
		try {
			ChannelFuture f=client.connect(host,port).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			boss.shutdownGracefully();
		}
	}	
	public static void main(String args[]){
		new EchoClient().connet("127.0.0.1", 9999);
	}

}
