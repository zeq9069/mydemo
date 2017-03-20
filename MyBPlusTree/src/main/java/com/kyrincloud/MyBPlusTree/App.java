package com.kyrincloud.MyBPlusTree;

import java.util.LinkedList;
import java.util.List;

/**
 * Hello world!
 * list的add(index,obj) 方法测试
 */
public class App {
    public static void main( String[] args ){
    	
    	List<String> list = new LinkedList<String>();
    	list.add("0");
    	list.add("1");
    	list.add("2");
    	list.add("3");
    	list.add("4");
    	list.add("5");
    	list.add("6");
    	list.add("7");
    	
    	list.add(0, "11");
    	
    	for(String s : list)
    	System.out.println(s);
    	
    	
    }
}
