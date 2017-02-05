package com.kyrincloud.JMIDemo;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *  one service inteface
 * @author kyrin
 *
 */
public interface GreetingService extends Remote{

	public String sayHello() throws RemoteException;
	
	public String sayName(String name) throws RemoteException;
	
}
