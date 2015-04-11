package com.demo.NettyDemo.example.eight.server.factory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.HashMap;
import java.util.Map;

import com.demo.NettyDemo.example.eight.message.Response;
import com.demo.NettyDemo.example.eight.server.handler.ServiceServerHandler;


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
public class ServerFactory extends AbstractServerFactory{
	
	
	
	private final NioEventLoopGroup bossGroup=new NioEventLoopGroup();
	private final NioEventLoopGroup workGroup=new NioEventLoopGroup();
	private ChannelFuture future=null;
	private static Map<String,String> classMap=new HashMap<String, String>();
	static{
		classMap.put("com.demo.NettyDemo.example.eight.service.UserService","com.demo.NettyDemo.example.eight.UserServiceImpl");
	}
	public void start(int port) {
		ServerBootstrap server=new ServerBootstrap();
		server.group(bossGroup, workGroup);
		server.channel(NioServerSocketChannel.class);
		server.option(ChannelOption.SO_BACKLOG, 100);
		server.option(ChannelOption.SO_KEEPALIVE, false);
		server.option(ChannelOption.SO_RCVBUF, 1024);
		server.option(ChannelOption.SO_SNDBUF, 1024);
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
			System.out.println("--------------服务端启动---------------------");
			future=server.bind(port).sync();
			System.out.println("create by: kyrin(云中鹤) kyrincloud@qq.com");
			System.out.println("--------------服务端启动完毕------------------");
		} catch (InterruptedException e) {
			System.out.println("--------------服务端启动失败------------------");
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
	public void stop() {
		bossGroup.shutdownGracefully();
		workGroup.shutdownGracefully();
	}
	public void sendResponse(Response response){
		ChannelFuture f=future.channel().writeAndFlush(response);
		f.addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture future) throws Exception {
				if(future.isSuccess()){
					System.out.println("[服务端发送response成功]");
				}else{
					System.out.println("[服务端发送response失败]");
					future.cause().printStackTrace();
				}
			}
		});
	}

	private static class SingletonHolder {
		static final ServerFactory instance = new ServerFactory();
	}

	public static ServerFactory getInstance() {
		return SingletonHolder.instance;
	}
}
