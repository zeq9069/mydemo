package com.kyrincloud.RPCDemo.service;

import java.util.List;
import java.util.Map;


public class HelloImpl implements IHello{

	public <T>T say(T msg) {
		System.out.println(this.getClass().getName()+"执行结果："+msg);
		return msg;
	}
	
	public <T> T sayMsg(T msg) {
		System.out.println(this.getClass().getName()+"执行结果："+msg);
		return msg;
	}

	public int say(int msg) {
		System.out.println(this.getClass().getName()+"执行结果："+msg);
		return msg;
	}

	public double say(double msg) {
		System.out.println(this.getClass().getName()+"执行结果："+msg);
		return msg;
	}

	public Long say(Long msg) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, String> say(Map<String, String> msg) {
		System.out.println(this.getClass().getName()+"执行结果："+msg);
		return msg;
	}

	public List<Integer> say(List<Integer> msg) {
		System.out.println(this.getClass().getName()+"执行结果："+msg);
		return msg;
	}

	public byte say(byte msg) {
		System.out.println(this.getClass().getName()+"执行结果："+msg);
		return msg;
	}

}
