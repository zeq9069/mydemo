package com.kyrincloud.MysqlSharding.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ShardingResultSet extends AbstractShardingResultSetWrapper {

	private List<ResultSet> resultSets;
	
	public ShardingResultSet(ResultSet rs) {
		super(rs);
	}
	
	public ShardingResultSet(List<ResultSet> resultSets) {
		super(resultSets.get(0));
		this.resultSets=resultSets;
	}

	@Override
	public boolean next() throws SQLException {
		if(rs.next()){
			return true;
		}
		if(resultSets!=null){
			for(int i=resultSets.indexOf(rs)+1;i<resultSets.size();){
				setCurrentResultSet(resultSets.get(i));
				return rs.next();
			}
		}
		return false;
	}

	@Override
	public boolean previous() throws SQLException {
		return rs.previous();
	}

}
