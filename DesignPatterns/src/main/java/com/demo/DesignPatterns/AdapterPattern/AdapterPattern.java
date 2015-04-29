package com.demo.DesignPatterns.AdapterPattern;
/**
 * ***********************
 * 
 *  适配器模式
 *  
 *  	适配器模式将某个类的接口转换成客户端期望的另一个接口表示，
 *  目的是消除由于接口不匹配所造成的类的兼容性问题。
 *  	主要分为三类：类的适配器模式、对象的适配器模式、接口的适配器模式。
 *
 *
 *	类的适配器模式：当希望将一个类转换成满足另一个新接口的类时，可以使用类的适配器模式，创建一个新类，继承原有的类，实现新的接口即可。
 *	对象的适配器模式：当希望将一个对象转换成满足另一个新接口的对象时，可以创建一个Wrapper类，持有原类的一个实例，在Wrapper类的方法中，调用实例的方法就行。
 *	接口的适配器模式：当不希望实现一个接口中所有的方法时，可以创建一个抽象类Wrapper，实现所有方法，我们写别的类的时候，继承抽象类即可。
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *	
 * @date [2015年4月29日]
 *
 */
public class AdapterPattern {
	
	public static void main(String a[]){
		System.out.println("**************类适配器***************");
		
		//<1>类适配器
		ClassAdapter classAdapter=new ClassAdapter();
		classAdapter.method1();
		classAdapter.method2();
		
		System.out.println("**************对象适配器*************");
		
		//<2>对象适配器
		Wrapper wrapper=new Wrapper(new Source());
		wrapper.method1();
		wrapper.method2();
		
		System.out.println("**************接口适配器*************");

		
		//<3>接口适配器
		
		 Sourceable source1 = new SourceSub1();  
	     Sourceable source2 = new SourceSub2();  
	          
	     source1.method1();  
	     source1.method2();  
	     source2.method1();  
	     source2.method2();  
		
		
	}
}
