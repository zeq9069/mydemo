package com.kyrincloud.RPCDemo.codec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class MyCodec {
	
	
	public static Object decode(byte [] obj) throws IOException{
		Object object=null;
		
		try {
			ByteArrayInputStream bais=new ByteArrayInputStream(obj);
			ObjectInputStream ois=new ObjectInputStream(bais);
			object=ois.readObject();
			bais.close();
			ois.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public static byte[] encode(Object obj){
		byte[] bytes=null;
		try {
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			ObjectOutputStream oos=new ObjectOutputStream(baos);
			oos.writeObject(obj);
			bytes=baos.toByteArray();
			baos.close();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}
	
	
}
