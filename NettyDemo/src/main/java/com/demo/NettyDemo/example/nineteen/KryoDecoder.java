package com.demo.NettyDemo.example.nineteen;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.io.Input;

public class KryoDecoder extends ByteToMessageDecoder {

	@SuppressWarnings("unchecked")
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		byte[] b = new byte[in.readableBytes()];
		in.readBytes(b, in.readerIndex(), in.readableBytes());
		ByteArrayInputStream is = new ByteArrayInputStream(b);
		ObjectInputStream ois = new ObjectInputStream(is);
		Kryo k = new Kryo();
		Input i = new Input(ois);
		Registration r = k.readClass(i);
		Object obj = k.readObject(i, r.getType());
		out.add(obj);
	}
}
