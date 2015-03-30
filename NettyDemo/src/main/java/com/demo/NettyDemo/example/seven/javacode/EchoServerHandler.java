package com.demo.NettyDemo.example.seven.javacode;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * ***********************
 *   netty 练习
 * 
 * java序列化编码解码
 * 
 *  服务端处理类
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年3月30日]
 *
 */
public class EchoServerHandler extends ChannelHandlerAdapter{

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		UserInfo userInfo=(UserInfo)msg;
		System.out.println("姓名："+userInfo.getName()+"\n"+"密码:"+userInfo.getPassword()+"\n"+"时间："+userInfo.getDate());
		ctx.write(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	
	

}
