package com.demo.NettyDemo.example.eight.client.factory;

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

import com.demo.NettyDemo.example.eight.client.handler.ServiceClientHandler;
import com.demo.NettyDemo.example.eight.message.Request;


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
public class ClientFactory extends AbstractClientFactory{

	

	private static final Bootstrap client=new Bootstrap();
	protected static NioEventLoopGroup bossGroup=new NioEventLoopGroup();

	private static String host;
	private static  int port;
	
	private static class SingletonHolder {
		static final ClientFactory instance = new ClientFactory();
	}

	public static ClientFactory getInstance() {
		return SingletonHolder.instance;
	}
	
	
	@SuppressWarnings("static-access")
	public void connect(String host,int port) {
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
			System.out.println("-------客户端开始启动--------");
			System.out.println("create by: Kyrin(云中鹤)");
			System.out.println("-------客户端启动完毕--------");
			this.host=host;
			this.port=port;
	}
	
	public  ChannelFuture createClient() throws Exception{
		ChannelFuture future=null;
		try {
			 future=client.connect(host,port).sync();
			    future.awaitUninterruptibly();
			 if (!future.isDone()) {
			     System.out.println("Create connection to " + host + ":" + port + " timeout!");
			      throw new Exception("Create connection to " + host + ":" + port + " timeout!");
			    }
			    if (future.isCancelled()) {
			     System.out.println("Create connection to " + host + ":" + port + " cancelled by user!");
			      throw new Exception("Create connection to " + host + ":" + port + " cancelled by user!");
			    }
			    if (!future.isSuccess()) {
			    	 System.out.println("Create connection to " + host + ":" + port + " error："+future.cause());
			      throw new Exception("Create connection to " + host + ":" + port + " error:"+future.cause());
			    }
		} catch (InterruptedException e) {
			bossGroup.shutdownGracefully();
		}
		return future;
	}
	
	public void stop() {
		bossGroup.shutdownGracefully();
	}
	
	public void sendRequest(ChannelFuture future,Request request){
			if(!future.channel().isOpen()){
				return;
			}
		ChannelFuture f=future.channel().writeAndFlush(request);
		f.addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture cf) throws Exception {
				if(cf.isSuccess()){
					System.out.println("--------客户端发送完成--------");
				}else{
					System.out.println("--------客户端发送失败--------");
					cf.cause().printStackTrace();
				}
				//cf.channel().close();
			}
		});
	}
}
