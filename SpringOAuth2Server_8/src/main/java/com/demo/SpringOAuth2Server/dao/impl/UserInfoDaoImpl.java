package com.demo.SpringOAuth2Server.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import com.demo.SpringOAuth2Server.dao.UserInfoDao;
import com.demo.SpringOAuth2Server.domain.UserInfo;

@Component
public class UserInfoDaoImpl extends JdbcDaoSupport implements UserInfoDao {

	private Logger logger=Logger.getLogger(UserInfoDaoImpl.class);
	
	@Autowired
	public UserInfoDaoImpl(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	
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
					userInfo.setIsLocked(rs.getInt("status"));
					userInfo.setDesc(rs.getString("desc"));
					//userInfo.setUserType(rs.getString("user_type"));
					//userInfo.setCreateDate(rs.getDate("create_date"));
					//userInfo.setCreateOriginIp(rs.getString("create_origin_ip"));
					//userInfo.setLastLoginDate(rs.getDate("last_login_date"));
					//userInfo.setLastLoginIp(rs.getString("last_login_ip"));
					//userInfo.setLastLoginOriginApp(rs.getString("last_login_origin_app"));
					//userInfo.setLastUpdateDate(rs.getDate("last_update_date"));
					//userInfo.setLastUpdateOriginApp(rs.getString("last_update_origin_app"));
					//userInfo.setLastUpdateOriginIp(rs.getString("last_update_origin_ip"));
					//userInfo.setUnlockedDate(rs.getDate("unlocked_date"));
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
	
}