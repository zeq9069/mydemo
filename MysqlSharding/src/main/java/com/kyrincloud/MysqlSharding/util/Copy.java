package com.kyrincloud.MysqlSharding.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class Copy {
	
	public static <T> T copy(T clazz) throws Exception{
		 ByteArrayOutputStream bos = new ByteArrayOutputStream();  
	        ObjectOutputStream oos = new ObjectOutputStream(bos);  
	  
	        oos.writeObject(clazz);  
	        // 将流序列化成对象  
	        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());  
	  
	        ObjectInputStream ois = new ObjectInputStream(bis);  
	  
	        return (T)ois.readObject();  
	}

}
