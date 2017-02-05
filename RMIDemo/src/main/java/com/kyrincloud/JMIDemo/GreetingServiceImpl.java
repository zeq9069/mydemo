package com.kyrincloud.JMIDemo;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import javax.naming.Context;
import javax.naming.InitialContext;

public class GreetingServiceImpl extends UnicastRemoteObject implements GreetingService{

	private static final long serialVersionUID = 6107055180482582172L;
	
	protected GreetingServiceImpl() throws RemoteException {
		super();
	}

	public String sayHello() {
		return "hello kyrin!";
	}
	

	public String sayName(String name) throws RemoteException {
		return "My name is "+name;
	}
	
	public static void main(String[] args) {
		
		try {
			GreetingService greet = new GreetingServiceImpl();
            
			LocateRegistry.createRegistry(8888); 

		    Context namingContext = new InitialContext(); 

		    //Naming.rebind("rmi://127.0.0.1:8888/GreetingService", greet);
		    namingContext.rebind("rmi://127.0.0.1:8888/GreetingService", greet);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
