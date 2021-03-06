package com.kyrincloud.MysqlSharding.preparedstatment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kyrincloud.MysqlSharding.resultset.ShardingResultSet;

public class ShardingPreparedStatment extends AbstractShardingPreparedStatmentWrapper {

	private List<PreparedStatement> ps;
	
	public ShardingPreparedStatment(List<PreparedStatement> ps) throws SQLException {
		this.ps=ps;
	}

	@Override
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		boolean result=false;
		for(PreparedStatement p:ps){
			setParameters(p, getParameters());
			result=p.execute(sql,autoGeneratedKeys);
			if(!result){
				throw new SQLException("execute 执行失败");
			}
		}
		return result;
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		boolean result=false;
		for(PreparedStatement p:ps){
			setParameters(p, getParameters());
			result=p.execute(sql,columnIndexes);
			if(!result){
				throw new SQLException("execute 执行失败");
			}
		}
		return result;
	}

	@Override
	public boolean execute(String sql, String[] columnNames) throws SQLException {
		boolean result=false;
		for(PreparedStatement p:ps){
			setParameters(p, getParameters());
			result=p.execute(sql,columnNames);
			if(!result){
				throw new SQLException("execute 执行失败");
			}
		}
		return result;
	}

	@Override
	public boolean execute(String sql) throws SQLException {
		boolean result=false;
		for(PreparedStatement p:ps){
			setParameters(p, getParameters());
			result=p.execute(sql);
			if(!result){
				throw new SQLException("execute 执行失败");
			}
		}
		return result;
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		List<ResultSet> list = new ArrayList<ResultSet>();
		for(PreparedStatement p:ps){
			list.add(p.executeQuery(sql));
		}
		return new ShardingResultSet(list);
	}

	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		int i=0;
		for(PreparedStatement p:ps){
			setParameters(p, getParameters());
			i+=p.executeUpdate(sql,autoGeneratedKeys);
		}
		return i;
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		int i=0;
		for(PreparedStatement p:ps){
			setParameters(p, getParameters());
			i+=p.executeUpdate(sql,columnIndexes);
		}
		return i;
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		int i=0;
		for(PreparedStatement p:ps){
			setParameters(p, getParameters());
			i+=p.executeUpdate(sql,columnNames);
		}
		return i;
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {
		int i=0;
		for(PreparedStatement p:ps){
			setParameters(p, getParameters());
			i+=p.executeUpdate(sql);
		}
		return i;
	}

	@Override
	public Connection getConnection() throws SQLException {
		throw new SQLException();
	}

	@Override
	public boolean execute() throws SQLException {
		boolean result=false;
		for(PreparedStatement p:ps){
			setParameters(p, getParameters());
			result=p.execute();
			if(!result){
				throw new SQLException("execute 执行失败");
			}
		}
		return result;
	}

	@Override
	public ResultSet executeQuery() throws SQLException {
		List<ResultSet> list = new ArrayList<ResultSet>();
		for(PreparedStatement p:ps){
			setParameters(p, getParameters());
			list.add(p.executeQuery());
		}
		return new ShardingResultSet(list);
	}

	@Override
	public int executeUpdate() throws SQLException {
		int i=0;
		for(PreparedStatement p:ps){
			setParameters(p, getParameters());
			i+=p.executeUpdate();
		}
		return i;
	}
	
	private void setParameters(PreparedStatement preparedStatement,List<Object> parameters) throws SQLException{
		int i=1;
		for(Object obj:parameters){
			preparedStatement.setObject(i++, obj);
		}
	}
}
