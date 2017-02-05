package com.kyrincloud.JMIDemo;


import javax.naming.Context;
import javax.naming.InitialContext;

public class Client {

	public static void main(String[] args) {
		try {
			Context namingContext = new InitialContext();
			//GreetingService greet = (GreetingService) Naming.lookup("rmi://127.0.0.1:8888/GreetingService");
			GreetingService greet = (GreetingService) namingContext.lookup("rmi://127.0.0.1:8888/GreetingService");

			for(int i=0;i<10;i++){
				System.out.println(greet.sayHello());
				System.out.println(greet.sayName("kyrin"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
