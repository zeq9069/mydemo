package com.kyrincloud.MysqlSharding;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class ShardingDataSource extends AbstractShardingDataSourceWrapper {

	public ShardingDataSource(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Connection getConnection() throws SQLException {
		System.out.println("---获取连接---");
		return new ShardingConnection(dataSource.getConnection());
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		System.out.println("---获取连接---username,password");
		return new ShardingConnection(dataSource.getConnection(username, password));
	}

}
