package com.kyrin.OltuDemo.shiro.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 序列化，反序列化工具
 * Redis 的key 值如果是String类型的话，必须直接通过String.getBytes()去序列化，跟 @StringRedisSerializer保持一样的原理
 * 如果跟redis key默认的序列化不一致会出现key中出现不明字符 '\xac\xed\x00\x05t\x009'
 * @author kyrin
 *
 */
public final class SerializationUtil {


	public static byte[] serialize(Object object) {
		if (object == null) {
			throw new NullPointerException("object Can't serialize null");
		}
		//如果是String 直接获取byte[]
		if(object instanceof String){
			return ((String) object).getBytes();
		}
		byte[] result=null;
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			oos.flush();
			result=baos.toByteArray();
		}
		catch (IOException ex) {
			throw new IllegalArgumentException("Failed to serialize object of type: " + object.getClass(), ex);
		}finally{
			close(oos);
			close(baos);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> T deserialize(byte[] bytes,	Class<? extends T> clazz) {
		if (bytes == null) {
			throw new NullPointerException("object Can't serialize null");
		}
		ObjectInputStream ois=null;
		try {
			ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
			return (T)ois.readObject();
		}
		catch (IOException ex) {
			throw new IllegalArgumentException("Failed to deserialize object", ex);
		}
		catch (ClassNotFoundException ex) {
			throw new IllegalStateException("Failed to deserialize object type", ex);
		}finally{
			close(ois);
		}
	}
	
	private static void close(Closeable close){
		if(close ==null){
			return;
		}
		try {
			close.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
