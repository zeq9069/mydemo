package com.kyrin.MybatisDemo;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.kyrin.MybatisDemo.domain.User;

/**
 * Hello world!
 *
 */
public class Bootstrap {

	static InputStream is;
	static SqlSessionFactoryBuilder build;
	static SqlSessionFactory ssf;
	static String config = "config.xml";

	static {
		is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(config);
		build = new SqlSessionFactoryBuilder();
		ssf = build.build(is, "development");
	}

	public static User selectOne() {
		SqlSession session = ssf.openSession();
		String statement = "com.kyrin.MybatisDemo.mapping.userMapper.getUser";
		try {
			return session.selectOne(statement, 1);
		} finally {
			session.close();
		}
	}
	
	public static int addUser(User user){
		SqlSession session = ssf.openSession();
		String statement = "com.kyrin.MybatisDemo.mapping.userMapper.addUser";
		try {
			int number=session.insert(statement,user);
			session.commit();
			return number;
		} finally {
			session.close();
		}
	}
	
	public static int deleteUser(int id){
		SqlSession session = ssf.openSession();
		String statement = "com.kyrin.MybatisDemo.mapping.userMapper.deleteUser";
		try {
			int number=session.delete(statement,id);
			session.commit();
			return number;
		} finally {
			session.close();
		}
	}
	
	public static int updateUser(User user){
		SqlSession session = ssf.openSession();
		String statement = "com.kyrin.MybatisDemo.mapping.userMapper.updateUser";
		try {
			int number=session.update(statement,user);
			session.commit();
			return number;
		} finally {
			session.close();
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println(selectOne());
		System.out.println("The number of rows affected by the insert : "+addUser(new User("Jack", "jack@gmail.com", "123")));
		System.out.println("The number of rows affected by the delete : "+deleteUser(13));
		User user=new User("Jack", "jack@qq.com", "123");
		user.setId(1);
		System.out.println("The number of rows affected by the update : "+updateUser(user));
		
		
	}
}
