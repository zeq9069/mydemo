package com.kyrincloud.MysqlSharding;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

public class ShardingDataSource extends AbstractShardingDataSourceWrapper {

	private List<DataSource> dataSources;
	
	private List<Connection> connections;
	
	public ShardingDataSource( List<DataSource> dataSources) throws SQLException {
		this.dataSources = dataSources;
		connections = createConn();
	}

	@Override
	public Connection getConnection() throws SQLException {
		System.out.println("---获取连接---");
		return new ShardingConnection(connections);
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		System.out.println("---获取连接---username,password");
		return new ShardingConnection(connections);
	}
	
	private List<Connection> createConn() throws SQLException{
		List<Connection> connections=new ArrayList<Connection>();
		for(DataSource ds:dataSources){
			connections.add(ds.getConnection());
		}
		return connections;
	}

}
