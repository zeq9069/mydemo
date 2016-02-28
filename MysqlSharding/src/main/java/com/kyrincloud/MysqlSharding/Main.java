package com.kyrincloud.MysqlSharding;


import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.sql.DataSource;

import com.kyrincloud.MysqlSharding.datasource.ShardingDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 在执行SQL之前自定义一些动作,实现多数据源，多preparedStatment，多ResultSet的合并
 * @author kyrin
 *
 */
public class Main {
	
	String sql ;
	static DataSource ds1 ;
	static DataSource ds2;
	
	static{
		try {
			ds1 = createDataSource("ds_0");
			ds2= createDataSource("ds_1");
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws SQLException, PropertyVetoException  {
		select();
	}

	public static DataSource createDataSource(String ds) throws PropertyVetoException {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDataSourceName(ds);
		cpds.setDriverClass("com.mysql.jdbc.Driver");
		cpds.setJdbcUrl(String.format("jdbc:mysql://localhost:3306/%s",ds));
		cpds.setUser("root");
		cpds.setPassword("root");
		return cpds;
	}
	
	public static  void select() throws SQLException, PropertyVetoException{
		String sql = "select * from user_table";
		ShardingDataSource dataSource = new ShardingDataSource(Arrays.asList(ds1,ds2));
		Connection conn = dataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(String.format("id = %d , username = %s , email = %s", rs.getInt("id"),
					rs.getString("name"), rs.getString("email")));
		}
	}
	
	public static  void insert() throws SQLException, PropertyVetoException{
		String sql = "insert into user_table(name,email) values(?,?)";
		ShardingDataSource dataSource = new ShardingDataSource(Arrays.asList(ds1,ds2));
		Connection conn = dataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, "www");
		ps.setString(2, "www@gmail.com");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(String.format("order_id = %s , user_id = %s , status = %s", rs.getInt("order_id"),
					rs.getInt("user_id"), rs.getString("status")));
		}
	}

}
