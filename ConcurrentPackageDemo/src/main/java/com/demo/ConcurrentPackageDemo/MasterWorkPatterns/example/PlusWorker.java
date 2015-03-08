package com.demo.ConcurrentPackageDemo.MasterWorkPatterns.example;

import com.demo.ConcurrentPackageDemo.MasterWorkPatterns.Work;

/**
 * **************************
 * 
 * master－worker设计模式例子：
 * 
 * 计算：1^3+2^3+3^3+4^3+5^3+6^3……＋100^3的结果
 * 
 * 
 * **************************
 * @author kyrin [2015年3月8日]
 *
 */
public class PlusWorker extends Work{

	@Override
	public Object handle(Object input) {
		int i=(Integer)input;
		return i*i*i;
	}
	
}
