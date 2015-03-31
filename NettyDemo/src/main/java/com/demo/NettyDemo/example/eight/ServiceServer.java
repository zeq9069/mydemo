package com.demo.NettyDemo.example.eight;


import java.util.HashMap;
import java.util.Map;

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

/**
 * ********************************
 *    netty 练习
 *    
 *   RPC：利用JDK和cglib实现远程服务调用
 *   编码：java序列化编码
 *
 * ********************************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年3月31日]
 *
 */
public class ServiceServer {
	
	//private int thread_num=Runtime.getRuntime().availableProcessors()*2; //CPU内核数
	
	Map<String, String> classMap=new HashMap<String,String>();
	public void init(){
		classMap.put("com.demo.NettyDemo.example.eight.service.UserService","com.demo.NettyDemo.example.eight.UserServiceImpl");
	}
	
	public  void start(int port,int timeout){
		NioEventLoopGroup bossGroup=new NioEventLoopGroup();
		NioEventLoopGroup workGroup=new NioEventLoopGroup();
		ServerBootstrap server=new ServerBootstrap();
		server.group(bossGroup,workGroup);
		server.channel(NioServerSocketChannel.class);
		server.option(ChannelOption.SO_BACKLOG, 100);
		server.option(ChannelOption.SO_KEEPALIVE, false);
		server.option(ChannelOption.SO_RCVBUF, 1024);
		server.option(ChannelOption.SO_SNDBUF, 1024);
		server.option(ChannelOption.SO_TIMEOUT, timeout);
		server.option(ChannelOption.TCP_NODELAY, true);
		server.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast((new ObjectDecoder(1024*1024, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader()))));
				ch.pipeline().addLast(new ObjectEncoder());
				ch.pipeline().addLast(new ServiceServerHandler());
			}
		});
		try {
			ChannelFuture future=server.bind(port).sync();
			System.out.println("--------------服务端启动--------------------");
			System.out.println("create by: kyrin(云中鹤) kyrincloud@qq.com");
			System.out.println("--------------服务端启动完毕------------------");
			
			future.channel().closeFuture().sync();
			
			System.out.println("--------------服务端关闭--------------------");
		} catch (InterruptedException e) {
			System.out.println("--------------服务端启动失败-----------------");
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
	
	public static void main(String a[]){
		ServiceServer server=new ServiceServer();
		server.init();
		server.start(9090, 3600);
	}
	
	
}
