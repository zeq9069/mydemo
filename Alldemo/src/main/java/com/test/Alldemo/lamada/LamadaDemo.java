package com.test.Alldemo.lamada;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class LamadaDemo
{
	//定义函数，只包含一个方法
	 interface lambdaInterface {  
	        public void me(String str1,String str2);  
	 }  
	 
	
	public static void main(String[] args) throws Exception {
		
		List<String> list=new ArrayList<String>();
		for(int i=0;i<100;i++){
			list.add(i+"");
		}
		long start=System.currentTimeMillis();
		list.forEach(new Consumer<String>() {
			@Override
			public void accept(String t) {
				System.out.println(t);
			}
		});
		long end=System.currentTimeMillis();
		System.out.println("遍历总耗时:"+(end-start));
		
		Stream<String> stream=list.stream();
		list.clear();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("3");
		//stream操作
		System.out.println(stream.filter(m->Integer.parseInt(m)+3==5).distinct().mapToInt(w -> Integer.parseInt(w)).sum());
		
		
		Runnable run=() -> {System.out.println("hello");};
		run.run();
		
		
		
		
		lambdaInterface l=(String w,String e)-> {System.out.println(w+e);};
		l.me("wwww","eee");
		
		
		new Thread(()->{
			System.out.println("The Thread start!");
		}).start();
		
		Callable<String> call=()->{return "hello callable !";};
		System.out.println(String.format("callable返回值为:%s",call.call()));
		
		
	}
}
