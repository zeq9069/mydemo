package com.demo.NettyDemo.example.twenty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;

import java.nio.charset.Charset;
import java.util.Date;

public class WebSocketHandler extends SimpleChannelInboundHandler<Object> {

	private WebSocketServerHandshaker handshaker;

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelActive");
		//ClientCache.put(System.currentTimeMillis(), ctx.channel());
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	public void handleHttpRquest(ChannelHandlerContext ctx, FullHttpRequest msg) {
		if (!msg.decoderResult().isSuccess() || !("websocket".equals(msg.headers().get("Upgrade")))) {
			sendHttpResponse(ctx, msg,
					new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
			return;
		}

		//构造握手响应返回，本地测试
		WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(
				"http://localhost:9999/websocket", null, false);

		handshaker = factory.newHandshaker(msg);
		if (handshaker == null) {
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
		} else {
			handshaker.handshake(ctx.channel(), msg);
		}
	}

	public void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest msg, FullHttpResponse response) {
		if (response.status().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(response.status().toString(), Charset.defaultCharset());
			response.content().writeBytes(buf);
			buf.release();
			response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
		}
		ChannelFuture cf = ctx.channel().writeAndFlush(response);
		if (!msg.headers().get(HttpHeaderNames.CONNECTION).equals(HttpHeaderValues.KEEP_ALIVE)
				|| response.status().code() != 200) {
			cf.addListener(ChannelFutureListener.CLOSE);
		}
	}

	public void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame msg) {
		//判断是否关闭链路的命令
		if (msg instanceof CloseWebSocketFrame) {
			handshaker.close(ctx.channel(), (CloseWebSocketFrame) msg.retain());
			return;
		}

		//判断是否是ping消息
		if (msg instanceof PingWebSocketFrame) {
			ctx.write(new PongWebSocketFrame(msg.content().retain()));
			return;
		}

		//本例子只支持文本消息，不支持二进制消息
		if (!(msg instanceof TextWebSocketFrame)) {
			throw new UnsupportedOperationException(String.format("%s type not support!", msg.getClass().getName()));

		}

		//返回应答消息
		String request = ((TextWebSocketFrame) msg).text();
		System.out.println(String.format("%s recieved %s", ctx.channel(), request));
		ctx.channel().writeAndFlush(new TextWebSocketFrame("欢迎光临！这是websocket服务。现在时间：" + (new Date()).toString()));

	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		//http接入
		if (msg instanceof FullHttpRequest) {
			handleHttpRquest(ctx, (FullHttpRequest) msg);
		}
		//websocket接入
		if (msg instanceof WebSocketFrame) {
			handleWebSocketFrame(ctx, (WebSocketFrame) msg);
		}
	}
}
