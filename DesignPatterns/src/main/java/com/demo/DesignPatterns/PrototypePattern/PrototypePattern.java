package com.demo.DesignPatterns.PrototypePattern;

import java.io.IOException;

/**
 * ********************************************
 *   			原型模式
 *
 *		原型模式虽然是创建型的模式，但是与工程模式没有关系，
 *	从名字即可看出，该模式的思想就是将一个对象作为原型，对
 *	其进行复制、克隆，产生一个和原对象类似的新对象
 *
 * 当然，当涉及到cloneable这个接口，就涉及到深拷贝和浅拷贝的感念：
 * 
 * 浅复制：将一个对象复制后，基本数据类型的变量都会重新创建，而引用类型，指向的还是原对象所指向的。
 * 深复制：将一个对象复制后，不论是基本数据类型还有引用类型，都是重新创建的。简单来说，就是深复制进行了完全彻底的复制，而浅复制不彻底。
 * 
 *
 * *********************************************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月29日]
 *
 */
public class PrototypePattern {

	public static void main(String argsp[]) throws ClassNotFoundException, IOException, CloneNotSupportedException{
		Person person1=new Person();
		person1.setName("kyrin");
		Address address=new Address();
		address.setCity("北京");
		person1.setAddress(address);
		
		Person person2=person1.deepClone();
		Person person3=person1.clone();
		System.out.println(String.format("原型：I'm %s ,and come from %s !", person1.getName(),person1.getAddress().getCity()));
		System.out.println(String.format("深拷贝：I'm %s ,and come from %s !", person2.getName(),person2.getAddress().getCity()));
		System.out.println(String.format("浅拷贝：I'm %s ,and come from %s !", person3.getName(),person3.getAddress().getCity()));

		System.out.println("**************person1修改后**********************");
		
		
		person1.setName("Jack");
		person1.getAddress().setCity("郑州");;
		
		System.out.println(String.format("原型：I'm %s ,and come from %s !", person1.getName(),person1.getAddress().getCity()));
		System.out.println(String.format("深拷贝：I'm %s ,and come from %s !", person2.getName(),person2.getAddress().getCity()));
		System.out.println(String.format("浅拷贝：I'm %s ,and come from %s !", person3.getName(),person3.getAddress().getCity()));
		
		/**
		 * 在person1修改后，浅拷贝的person3的name没变，但是对象address的city变了，二深拷贝毫无影响！
		 * 
		 */
		
		
	}
	
}
