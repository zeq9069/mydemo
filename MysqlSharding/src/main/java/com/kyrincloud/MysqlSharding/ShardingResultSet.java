package com.kyrincloud.MysqlSharding;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShardingResultSet extends AbstractShardingResultSetWrapper {

	public ShardingResultSet(ResultSet rs) {
		super(rs);
	}

	@Override
	public boolean next() throws SQLException {
		System.out.println("---next 1---");
		return rs.next();
	}

	@Override
	public boolean previous() throws SQLException {
		System.out.println("---previous 1");
		return rs.previous();
	}

}
