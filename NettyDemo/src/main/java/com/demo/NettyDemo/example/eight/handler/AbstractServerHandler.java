package com.demo.NettyDemo.example.eight.handler;

import com.demo.NettyDemo.example.eight.message.Request;
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
public class AbstractServerHandler extends ChannelHandlerAdapter{
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
		System.out.println("[服务端异常退出]");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("服务端接收到消息ID："+((Request)msg).getId());
		handlerRequest(ctx,(Request)msg);
	}
	
	private void handlerRequest(ChannelHandlerContext ctx, Request request){
		//TODO 启动线程池中的县城去处理逻辑[处理request---->封装response------>ctx发送response]
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
}
