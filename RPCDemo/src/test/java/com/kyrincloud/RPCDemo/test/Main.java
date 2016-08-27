package com.kyrincloud.RPCDemo.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.kyrincloud.RPCDemo.proxy.ClientProxy;
import com.kyrincloud.RPCDemo.service.IHello;

public class Main {
	
	public static void main(String[] args) throws IOException {
		ClientProxy p = new ClientProxy();
		int i = 0;
		long start = System.currentTimeMillis();
		while (i < 100000) {

			IHello hello = (IHello) p.newInstance(IHello.class);
			String result = hello.sayMsg("222");
			int result1 = hello.say(12345);
			double result2 = hello.say(12345.0);
			Map<String, String> map = new HashMap<String, String>();
			map.put("1", "wwwwwwwwwww");
			Map<String, String> result3 = hello.say(map);
			System.out.println("执行结果为：\n" + result + "\n" + result1 + "\n"
					+ result2 + "\n" + result3.get("1"));
			i++;
			System.out.println(i);
		}
		System.out.println(System.currentTimeMillis()-start);
	}
}
