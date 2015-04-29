package com.demo.DesignPatterns.FactoryMethodPattern;

public class PersonFactory {
	
	
	//<1> 通过参数来创建对象
	public static Person product(String type){
		if(type.equals("man")){
			return new Man();
		}else if(type.equals("woman")){
			return new Woman();
		}
		System.out.println(String.format("Are You sure the  %s is Person ? ",type));
		return null;
	}
	
	
	//<2> 分别创建

	public static Person createMan(){
		return new Man();
	}
	 public static Person createWoman(){
		 return new Woman();
	 }
}
