package com.demo.NettyDemo.example.eight.factory;

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

import java.util.HashMap;
import java.util.Map;

import com.demo.NettyDemo.example.eight.ServiceClientHandler;
import com.demo.NettyDemo.example.eight.message.Request;
import com.demo.NettyDemo.example.eight.message.Response;

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
public abstract class AbstractClientFactory implements Client{
	
	private Map<Integer,Response> responseMap=new HashMap<Integer, Response>();
	private NioEventLoopGroup bossGroup=new NioEventLoopGroup();
	private ChannelFuture future=null;
	
	public void connect(String host,int port) {
		Bootstrap client=new Bootstrap();
		client.group(bossGroup);
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
			System.out.println("-------客户端开始启动--------");
			future=client.connect(host,port).sync();
			System.out.println("create by: Kyrin(云中鹤)");
			System.out.println("-------客户端启动完毕--------");
			
		} catch (InterruptedException e) {
			bossGroup.shutdownGracefully();
		}
	}
	public void stop() {
		bossGroup.shutdownGracefully();
	}
	public void sendRequest(Request request){
		ChannelFuture f=future.channel().writeAndFlush(request);
		f.addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture future) throws Exception {
				if(future.isSuccess()){
					System.out.println("--------发送完成--------");
				}else{
					System.out.println("--------发送失败--------");
				}
			}
		});
	}
	public void receiveResponse(Response response){
		responseMap.put(response.getId(), response);
	}
	public void removeReponse(Response response){
		responseMap.remove(response).getId();
	}
}
