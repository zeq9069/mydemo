package com.demo.springSecurity.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.demo.springSecurity.dao.UserInfoDao;
import com.demo.springSecurity.domain.Role;
import com.demo.springSecurity.domain.UserInfo;

public class UserInfoDaoImpl extends JdbcDaoSupport implements UserInfoDao {

	private Logger logger=Logger.getLogger(UserInfoDaoImpl.class);
	
	@Override
	public UserInfo findUserInfoByName(String username) {
		String sql="select * from users where username=?";
		UserInfo userInfo=null;
		try{
			userInfo=this.getJdbcTemplate().queryForObject(sql, new Object[]{username},new RowMapper<UserInfo>(){
				@Override
				public UserInfo mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					UserInfo userInfo=new UserInfo();
					userInfo.setId(rs.getInt("id"));
					userInfo.setName(rs.getString("username"));
					userInfo.setPassword(rs.getString("password"));
					userInfo.setStatus(rs.getInt("status"));
					userInfo.setDesc(rs.getString("desc"));
					userInfo.setRoles(getRolesByUserId(username));
					return userInfo;
				}
			});
			
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isInfoEnabled()){
				logger.info("用户名不存在");
			}
		}
		return userInfo;
	}
	
	private List<Role> getRolesByUserId(String username){
		String sql="select r.id,r.name,r.desc from users u ,role r, users_role ur where u.id=ur.user_id and r.id=ur.role_id and u.username=?";
		return this.getJdbcTemplate().query(sql,new String[]{username},new RowMapper<Role>(){
			@Override
			public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
				Role role=new Role();
				role.setId(rs.getInt("id"));
				role.setName(rs.getString("name"));
				role.setDesc(rs.getString("desc"));
				return role;
			}
		});
	}

}
