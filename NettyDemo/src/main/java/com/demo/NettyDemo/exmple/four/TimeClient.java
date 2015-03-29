package com.demo.NettyDemo.exmple.four;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * ***********************
 *   netty 练习
 *
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年3月29日]
 *
 */
public class TimeClient {

	public void connect(String host,int port){
		
		EventLoopGroup bossGroup=new NioEventLoopGroup();
		Bootstrap client=new Bootstrap();
		client.group(bossGroup);
		client.channel(NioSocketChannel.class);
		client.option(ChannelOption.TCP_NODELAY, true);
		client.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new TimeClientHandler("time"));
			}
		});
		ChannelFuture f=client.connect(host, port);
		try {
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			bossGroup.shutdownGracefully();
		}
		
	}
	
	public static void main(String a[]){
		int port=9090;
		String host="127.0.0.1";
		new TimeClient().connect(host, port);
	}
	
	
}
