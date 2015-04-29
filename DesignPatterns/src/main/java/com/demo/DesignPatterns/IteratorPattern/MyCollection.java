package com.demo.DesignPatterns.IteratorPattern;

public class MyCollection implements Collection{

	 public String string[] ;  
	 
	
	 public MyCollection(String [] string){
		 this.string=string;
	 }
	
	public Iterator iterator() {
		return new MyIterator(this);
	}

	public Object get(int i) {
		return string[i];
	}

	public int size() {
		return string.length;
	}

}
