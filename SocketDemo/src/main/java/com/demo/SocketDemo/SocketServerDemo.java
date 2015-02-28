package com.demo.SocketDemo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socket server
 *@author kyrin
 *
 */

public class SocketServerDemo {

	private static ServerSocket server;

	public static void method1() {
		try {
			Socket so;
			server = new ServerSocket(9090);
			while (true) {
				so = server.accept();
				Thread thread = new Thread(new ReaderThreadDemo(so.getInputStream(), so.getOutputStream()));
				thread.setDaemon(true);//设置守护线程
				thread.start();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		method1();
	}
}
