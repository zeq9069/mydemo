package com.demo.NettyDemo.example.eighteen;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.caucho.hessian.io.Hessian2Input;

/**
 * 
 * hession 解码 java对象必须实现序列化接口 
 * 
 * @author kyrin
 *
 */
public class HessianDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		byte[] bs = new byte[in.readableBytes()];
		in.readBytes(bs, in.readerIndex(), in.readableBytes());//将数据写进bs数组中
		ByteArrayInputStream bais = new ByteArrayInputStream(bs);
		Hessian2Input h2 = new Hessian2Input(bais);
		Object obj = h2.readObject();
		h2.close();
		out.add(obj);
	}
}
