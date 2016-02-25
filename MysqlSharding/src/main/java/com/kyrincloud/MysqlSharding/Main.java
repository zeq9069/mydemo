package com.kyrincloud.MysqlSharding;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 在执行SQL之前自定义一些动作
 * @author kyrin
 *
 */
public class Main {

	public static void main(String[] args) throws SQLException, PropertyVetoException {
		String sql = "select * from t_order_0";
		DataSource ds = createDataSource();
		ShardingDataSource dataSource = new ShardingDataSource(ds);
		Connection conn = dataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(String.format("order_id = %s , user_id = %s , status = %s", rs.getInt("order_id"),
					rs.getInt("user_id"), rs.getString("status")));
		}
	}

	public static DataSource createDataSource() throws PropertyVetoException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setDataSourceName("ds_0");
		ds.setDriverClass("com.mysql.jdbc.Driver");
		ds.setJdbcUrl("jdbc:mysql://localhost:3306/ds_0");
		ds.setUser("root");
		ds.setPassword("root");
		return ds;
	}

}
