package com.demo.NettyDemo.exmple.six;

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
		String body=(String)msg;
		
		System.out.println("服务端接收到来自客户端的消息："+body);
		/*分隔符
		ByteBuf response=Unpooled.copiedBuffer((body+"$_").getBytes());
		ctx.write(response);
		*/
		ctx.write(Unpooled.copiedBuffer(body.getBytes()));
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
}
