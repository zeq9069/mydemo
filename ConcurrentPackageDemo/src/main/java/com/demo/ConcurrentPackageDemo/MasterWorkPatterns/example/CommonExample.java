package com.demo.ConcurrentPackageDemo.MasterWorkPatterns.example;
/**
 * *********************************************************
 * 
 * 计算：1^3+2^3+3^3+4^3+5^3+6^3……＋100^3的结果
 * 
 * 此案例用来与master－work设计模式进行对比，看看效率上的不同
 * 
 * 其实，本案例的执行时间是比较快的，所以有时候设计模式是不一定是效率最高的
 * 
 * 
 * ********************************************************
 * 
 * @author kyrin [2015年3月8日]
 *
 */
public class CommonExample {
	
	public static void main(String a[]){
		long start=System.currentTimeMillis();
		int y=0;
		for(int i=1;i<=100;i++){
			y+=i*i*i;
		}
		System.out.println("最终结果为："+y);
		System.out.println("总耗时："+(System.currentTimeMillis()-start));
	}

}
