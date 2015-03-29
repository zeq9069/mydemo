package com.demo.NettyDemo.exmple.four;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * ***********************
 *  netty 练习
 *  
 *  time服务处理类
 * 
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年3月28日]
 *
 */
public class TimeServerHandler extends ChannelHandlerAdapter{

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("-----------channal active------------------ ");
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("-----------channal read-------------------- ");
		ByteBuf buf=(ByteBuf) msg;
		byte b[]=new byte[buf.readableBytes()];
		buf.readBytes(b);
		String body=new String(b,"UTF-8");
		System.out.println("接收到的请求命令为："+body);
		String response="time".equalsIgnoreCase(body)?(new Date()).toString():"error order";
		ByteBuf bb=Unpooled.copiedBuffer(response.getBytes());
		ctx.write(bb);
	}

	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}


}
