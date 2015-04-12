package com.demo.NettyDemo.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

/**
 * 
 * @author lenovo
 *
 */
@SuppressWarnings("unused")
public class EchoClientHandler extends ChannelHandlerAdapter {
	private static final Logger logger = Logger.getLogger(EchoClientHandler.class.getName());
	private final ByteBuf firstMessage;

	public EchoClientHandler(int firstMessageSize) {
		if (firstMessageSize <= 0) {
			throw new IllegalArgumentException("firstMessageSize: " + firstMessageSize);
		}
		this.firstMessage = Unpooled.buffer(firstMessageSize);
		for (int i = 0; i < firstMessage.capacity(); i++) {
			firstMessage.writeByte((byte) i);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
	}

}
