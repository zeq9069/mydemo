package com.test.Alldemo.nativeDemo;

public class Test {

	static{
		System.load("/Users/zhangerqiang/Desktop/Alldemo/target/classes/test.so");
	}
	
	private  native static int test();
	
	public static void main(String[] args) {
		System.out.println("动态库调用成功:"+Test.test());
	}
}
