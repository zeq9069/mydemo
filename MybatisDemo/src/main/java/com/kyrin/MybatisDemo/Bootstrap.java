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
    public static void main( String[] args ){
    	String config="config.xml";
    	InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(config);
    	SqlSessionFactoryBuilder build=new SqlSessionFactoryBuilder();
    	SqlSessionFactory ssf=build.build(is);
    	SqlSession session=ssf.openSession();
    	String statement="com.kyrin.MybatisDemo.mapping.userMapper.getUser";
    	User user=session.selectOne(statement,1);
    	System.out.println(user);
    }
}
