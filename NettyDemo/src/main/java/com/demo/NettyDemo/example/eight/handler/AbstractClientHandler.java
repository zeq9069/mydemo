package com.demo.NettyDemo.example.eight.handler;

import com.demo.NettyDemo.example.eight.factory.ClientFactory;
import com.demo.NettyDemo.example.eight.message.Request;
import com.demo.NettyDemo.example.eight.message.Response;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

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
public abstract class AbstractClientHandler extends ChannelHandlerAdapter{

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		System.out.println("[客户端异常退出]");
		ctx.channel().close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if(!(msg instanceof Response)){
			System.out.println("客户端接收消息异常");
		}
		System.out.println("[客户端接收到服务端的消息]："+msg.toString());
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
}
