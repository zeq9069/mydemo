package com.demo.ConcurrentPackageDemo.GuardedSuspensionPatterns;
/**
 * **************************
 *  
 *  GuardedSuspension
 *  
 * 请求实体
 * 
 * **************************
 * 
 * @author kyrin [2015年3月9日]
 *
 */
public class Request {
	
	private String name;
	
	public Request(String name) {
		this.name=name;
	}

	public String getName() {
		return name;
	}
	
	public String toString(){
		return "["+name+"]";
	}

}