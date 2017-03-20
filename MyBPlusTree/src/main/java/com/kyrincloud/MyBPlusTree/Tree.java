package com.kyrincloud.MyBPlusTree;

public interface Tree <K extends Comparable<K>,V>{
	
	public Integer get(Comparable<K> key);
	
	public void insertOrUpdate(Entry<K, V> entry);
	
	public void remove(Comparable<K> key);
	

}
