package com.kyrincloud.JMIDemo.multicastSocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * multicastSocket info
 * 
 * <a>https://docs.oracle.com/javase/7/docs/api/java/net/MulticastSocket.html</a>
 * 
 * 
 * A multicast group is specified by a class D IP address and by a standard UDP port number. 
 * Class D IP addresses are in the range 224.0.0.0 to 239.255.255.255, inclusive. 
 * The address 224.0.0.0 is reserved and should not be used.
 * 
 * NOTE:
 * 	Macbook has a error :  java.net.SocketException: Can't assign requested address
 * 		解决办法： Turn off wireless and use a wired connection (关掉无线网，使用有线)
 * 
 * 
 * send pack to self
 * 
 * @author kyrin
 */
public class MulticastSocketTest {
	

	public static void main(String[] args) {
		try {
			String msg = "hello kyrin";
			InetAddress address = InetAddress.getByName("228.5.6.7");
			MulticastSocket multi = new MulticastSocket(6789);
			multi.joinGroup(address);
			
			DatagramPacket pack = new DatagramPacket(msg.getBytes(), msg.length(),address,6789);
			
			for(int i = 0; i < 10 ; i++){
				
				multi.send(pack);//发送数据
				
				byte[] buf = new byte[1000];
				DatagramPacket recv = new DatagramPacket(buf, buf.length);
				multi.receive(recv);
				System.out.println("接收到数据："+new String(recv.getData()));
			}
			multi.leaveGroup(address);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
