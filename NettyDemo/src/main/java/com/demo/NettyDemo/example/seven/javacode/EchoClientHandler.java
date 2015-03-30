package com.demo.NettyDemo.example.seven.javacode;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * ***********************
 *   netty  练习
 *   
 *   
 *   
 *
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年3月30日]
 *
 */
public class EchoClientHandler extends ChannelHandlerAdapter{

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		UserInfo userInfo=new UserInfo();
		ctx.writeAndFlush(userInfo);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		UserInfo userInfo=(UserInfo) msg;
		System.out.println("姓名："+userInfo.getName()+"\n"+"密码:"+userInfo.getPassword()+"\n"+"时间："+userInfo.getDate());
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		
	}

	
	
	
}
