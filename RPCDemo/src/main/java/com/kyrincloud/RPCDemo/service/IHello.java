package com.kyrincloud.RPCDemo.service;

import java.util.List;
import java.util.Map;


public interface IHello{
	
	public <T>T sayMsg(T msg);
	
	public int say(int msg);
	
	public double say(double msg);

	public Long say(Long msg);
	
	public Map<String,String> say(Map<String,String> msg);
	
	public List<Integer> say(List<Integer> msg);
	
	public byte say(byte msg);
}
