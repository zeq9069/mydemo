package com.demo.DesignPatterns.BuilderPattern;

import java.util.ArrayList;
import java.util.List;

public class Builder {
	
	private List<Person> list=new ArrayList<Person>();
	
	public void buildYelloPerson(int count){
		for(int i=0;i< count;i++){
			list.add(new YelloPerson());
		}
	}
	
	public void buildBlackPerson(int count){
		for(int i=0;i< count;i++){
			list.add(new BlackPerson());
		}
	}
}
