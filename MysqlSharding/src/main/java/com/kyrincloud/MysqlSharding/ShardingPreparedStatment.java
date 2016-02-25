package com.kyrincloud.MysqlSharding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import com.kyrincloud.MysqlSharding.util.Copy;

public class ShardingPreparedStatment extends AbstractShardingPreparedStatmentWrapper {

	public ShardingPreparedStatment(PreparedStatement ps) {
		super(ps);
	}

	@Override
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		System.out.println("---execute 1---");
		return ps.execute(sql, autoGeneratedKeys);
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		System.out.println("---execute 2---");
		return ps.execute(sql, columnIndexes);
	}

	@Override
	public boolean execute(String sql, String[] columnNames) throws SQLException {
		System.out.println("---execute 3---");
		return ps.execute(sql, columnNames);
	}

	@Override
	public boolean execute(String sql) throws SQLException {
		System.out.println("---execute 4---");
		return ps.execute(sql);
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		System.out.println("---executeQuery 1---");
		return ps.executeQuery();
	}

	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		System.out.println("---executeUpdate 1---");
		return ps.executeUpdate(sql, autoGeneratedKeys);
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		System.out.println("---executeUpdate 2---");
		return ps.executeUpdate(sql, columnIndexes);
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		System.out.println("---executeUpdate 3---");
		return ps.executeUpdate(sql, columnNames);
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {
		System.out.println("---executeUpdate 4---");
		return ps.executeUpdate(sql);
	}

	@Override
	public Connection getConnection() throws SQLException {
		System.out.println("---getConnection 1---");
		return ps.getConnection();
	}

	@Override
	public boolean execute() throws SQLException {
		System.out.println("---execute 5---");
		return ps.execute();
	}

	@Override
	public ResultSet executeQuery() throws SQLException {
		System.out.println("---executeQuery 2---");
		ResultSet rs=ps.executeQuery();
		ResultSet rs2=null;
		try {
			rs2=Copy.copy(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ShardingResultSet(Arrays.asList(rs,rs2));
	}

	@Override
	public int executeUpdate() throws SQLException {
		System.out.println("---executeUpdate 5---");
		return ps.executeUpdate();
	}

}
