package com.demo.NettyDemo.example.four;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * ***********************
 *   netty 练习
 *
 *   time客户端服务处理类
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年3月29日]
 *
 */
public class TimeClientHandler extends ChannelHandlerAdapter{
	
	private ByteBuf mBuf;
	
	public TimeClientHandler(String message){
		ByteBuf buf=Unpooled.copiedBuffer(message.getBytes());
		this.mBuf=buf;
	}
	

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		System.out.println("--------------client exception---------------");
		ctx.close();
		
	}


	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("--------------client active---------------");
		ctx.writeAndFlush(mBuf);
	}


	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf buf=(ByteBuf)msg;
		byte b[]=new byte[buf.readableBytes()];
		buf.readBytes(b);
		String body=new String(b,"UTF-8");
		System.out.println("--------------client 接收到服务端传来的结果:"+body);
	}

	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.close();
	}

	
}
