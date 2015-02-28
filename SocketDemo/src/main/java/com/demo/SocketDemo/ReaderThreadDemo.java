package com.demo.SocketDemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * 
 * @author kyrin
 *
 */
public class ReaderThreadDemo implements Runnable {

	InputStream input;
	OutputStream output;

	public ReaderThreadDemo(InputStream in, OutputStream output) {
		this.input = in;
		this.output = output;
	}

	public void run() {
		String str = "";
		BufferedReader reader;
		InputStreamReader in;
		OutputStreamWriter writer;

		in = new InputStreamReader(input);//输入流
		writer = new OutputStreamWriter(output);//输出流
		reader = new BufferedReader(in);

		writer = new OutputStreamWriter(output);
		try {
			writer.write("");
			writer.write("127.0.0.1:9090 > ");
			writer.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (!str.equals("end")) {

			try {
				str = reader.readLine();
				writer.write("");
				writer.write(str);
				writer.write("\r\n");//CRLF符号，类似enter换行符
				writer.write("127.0.0.1:9090 > ");
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("服务器接收到了：" + str);
		}

		try {
			writer.write("exit success! Goode Bay!");
			writer.flush();
			input.close();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
