package com.kyrincloud.MysqlSharding;

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
		System.out.println("---next 1---");
		if(rs.next()){
			return true;
		}
		if(resultSets!=null){
			for(int i=1;i<resultSets.size();i++){
				System.out.println(i);
				setCurrentResultSet(resultSets.get(i));
			}
		}
		return rs.next();
	}

	@Override
	public boolean previous() throws SQLException {
		System.out.println("---previous 1");
		return rs.previous();
	}

}
