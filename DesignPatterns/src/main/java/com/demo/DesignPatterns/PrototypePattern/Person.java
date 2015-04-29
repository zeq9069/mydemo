package com.demo.DesignPatterns.PrototypePattern;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Person implements Cloneable,Serializable{
	
	String name;
	Address address;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	/*
	 * 浅拷贝
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Person clone() throws CloneNotSupportedException {
		return (Person)super.clone();
	}
	
	/*
	 * 深拷贝
	 * 
	 * 要实现深复制，需要采用流的形式读入当前对象的二进制输入，再写出二进制数据对应的对象。
	 *
	 */
	public Person deepClone() throws IOException, ClassNotFoundException{
		 /* 写入当前对象的二进制流 */ 
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		ObjectOutputStream objos=new ObjectOutputStream(os);
		
		objos.writeObject(this);
		
		/* 读出二进制流产生的新对象 */ 
		ByteArrayInputStream is=new ByteArrayInputStream(os.toByteArray());
		ObjectInputStream objis=new ObjectInputStream(is);
		
		return (Person) objis.readObject();
	}
	
	
	
}
