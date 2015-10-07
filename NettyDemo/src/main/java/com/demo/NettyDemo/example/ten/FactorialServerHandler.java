package com.demo.NettyDemo.example.ten;

import java.math.BigInteger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Handler for a server-side channel. This handler maintains stateful
 * information which is specific to a certain channel using member variables.
 * Therefore, an instance of this handler can cover only one channel. You have
 * to create a new handler instance whenever you create a new channel and insert
 * this handler to avoid a race condition.
 */
public class FactorialServerHandler extends
		SimpleChannelInboundHandler<BigInteger> {

	private BigInteger lastMultiplier = new BigInteger("1");
	private BigInteger factorial = new BigInteger("1");

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.err.printf("Factorial of %,d is: %,d%n", lastMultiplier,
				factorial);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, BigInteger msg)
			throws Exception {
		// TODO Auto-generated method stub
		lastMultiplier = msg;
		factorial = factorial.multiply(msg);
		ctx.writeAndFlush(factorial);
	}

}
