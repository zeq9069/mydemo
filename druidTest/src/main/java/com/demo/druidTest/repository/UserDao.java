package com.demo.druidTest.repository;

public interface UserDao<T,K>{

	public K add(T obj);
	
	public boolean delete(K id);
	
	public boolean update(T obj);
	
	public T findOne(K id);
	
}
