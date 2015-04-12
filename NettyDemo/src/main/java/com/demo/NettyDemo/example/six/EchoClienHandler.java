package com.demo.NettyDemo.example.six;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * *******************************
 *  netty 练习
 * 
 * 利用
 *  （1）DelimiterBaseFrameDecoder 
 *  （2）FixedLengthFrameDecoder
 *  
 *  来解决 粘包/拆包 带来的读半包的问题
 *
 * ******************************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年3月29日]
 *
 */
public class EchoClienHandler extends ChannelHandlerAdapter{
	
	private ByteBuf buf;
	
	public  EchoClienHandler(String message,String delimiter) {
		/*分隔符
		 * ByteBuf b=Unpooled.copiedBuffer((message+delimiter).getBytes());
		this.buf=b;
		*/
		this.buf=Unpooled.copiedBuffer(message.getBytes());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(buf);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		String body=(String)msg;
		System.out.println("client接收到服务端的回馈消息为："+body);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	
}
