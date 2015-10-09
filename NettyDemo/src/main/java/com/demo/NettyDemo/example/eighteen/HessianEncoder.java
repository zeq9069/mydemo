package com.demo.NettyDemo.example.eighteen;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;

import com.caucho.hessian.io.Hessian2Output;

/**
 *  hession 解码 java对象必须实现序列化接口 
 * @author kyrin
 *
 */
public class HessianEncoder extends MessageToByteEncoder<Object> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Hessian2Output oos = new Hessian2Output(os);
		oos.writeObject(msg);
		oos.close();
		out.writeBytes(os.toByteArray());
	}
}
