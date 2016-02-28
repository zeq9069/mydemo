package com.kyrincloud.MysqlSharding.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import com.kyrincloud.MysqlSharding.connection.ShardingConnection;

public class ShardingDataSource extends AbstractShardingDataSourceWrapper {

	private List<DataSource> dataSources;
	
	private List<Connection> connections;
	
	public ShardingDataSource( List<DataSource> dataSources) throws SQLException {
		this.dataSources = dataSources;
		connections = createConn();
	}

	@Override
	public Connection getConnection() throws SQLException {
		return new ShardingConnection(connections);
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
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
