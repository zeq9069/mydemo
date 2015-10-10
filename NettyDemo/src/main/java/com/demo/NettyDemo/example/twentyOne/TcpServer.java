package com.demo.NettyDemo.example.twentyOne;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import com.demo.NettyDemo.example.twenty.WebSocketHandler;

/**
 * 
 * @author kyrin
 *
 */
public class TcpServer {

	public static void main(String[] args) {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup work = new NioEventLoopGroup();

		ServerBootstrap server = new ServerBootstrap();
		server.group(boss, work);
		server.channel(NioServerSocketChannel.class);
		server.childHandler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pip = ch.pipeline();
				pip.addLast(new HttpServerCodec());
				pip.addLast(new HttpObjectAggregator(65535));
				pip.addLast(new ChunkedWriteHandler());
				pip.addLast(new WebSocketHandler());
			}
		});

		//定时给客户端发送消息
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Iterator<Channel> it = ClientCache.getList();
				while (it.hasNext()) {
					Channel ctx = it.next();
					ctx.writeAndFlush("轮询发送：" + System.currentTimeMillis());
				}

			}
		}, 1000, 1000 * 10);

		try {
			ChannelFuture cf = server.bind(9999).sync();
			cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			boss.shutdownGracefully();
			work.shutdownGracefully();
		}
	}
}
