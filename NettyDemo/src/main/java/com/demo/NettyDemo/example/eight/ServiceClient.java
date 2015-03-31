package com.demo.NettyDemo.example.eight;

import com.demo.NettyDemo.example.eight.message.Request;
import com.demo.NettyDemo.example.eight.message.Response;
import com.demo.NettyDemo.example.eight.service.UserService;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
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
public class ServiceClient {
	ChannelFuture future;
	private static NioEventLoopGroup boss=new NioEventLoopGroup();
	public void connet(String host,int port){
		Bootstrap client=new Bootstrap();
		client.group(boss);
		client.channel(NioSocketChannel.class);
		client.option(ChannelOption.SO_KEEPALIVE, false);
		client.option(ChannelOption.SO_RCVBUF, 1024);
		client.option(ChannelOption.SO_SNDBUF, 1024);
		client.option(ChannelOption.TCP_NODELAY, true);
		client.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new ObjectDecoder(1024*1024,ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
				ch.pipeline().addLast(new ObjectEncoder());
				ch.pipeline().addLast(new ServiceClientHandler());
			}
		});
		
		try {
			future=client.connect(host, port).sync();
			//future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			boss.shutdownGracefully();
		}
	}
	
	public void stop(){
		boss.shutdownGracefully();
	}
	
	public Response sendRequest(Request req){
		final Response res=null;
		ChannelFuture f=future.channel().writeAndFlush(req);
		f.addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture fu) throws Exception {
				if(fu.isSuccess()){
					System.out.println("-----");
				}
			}
		});
		
		return res;
	}
	
	
	
	public static void main(String a[]){
		ServiceClient client=new ServiceClient();
		client.connet("127.0.0.1", 9090);
		Request req=new Request();
		req.setArgs(null);
		req.setInterfaceName(null);
		req.setMethod(null);
		client.sendRequest(req);
		client.stop();
	}
}
