package com.demo.SpringOAuth2Server.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserInfo implements UserDetails{

	private static final long serialVersionUID = -6476235202408577967L;

	
	private int id;
	
	/*
	 * 用户名
	 */
	private String name;
	
	/*
	 * 密码
	 */
	private String password;
	
	/*
	 * 描述信息
	 */
	private String desc;
	
	/*
	 * 用户类型
	 */
	private String  userType;
	
	/************[管理信息]***********/
	
	/*
	 * 创建时间
	 */
	private Date createDate=new Date();
	
	/*
	 * 最后更改时间
	 */
	private Date lastUpdateDate=new Date();
	
	/*
	 * 最后登录时间
	 */
	private Date lastLoginDate=new Date();
	
	/*
	 * 最后创建源IP
	 */
	private String createOriginIp;
	
	/*
	 * 最后更改源应用
	 */
	private String lastUpdateOriginApp;
	
	/*
	 * 最后更改源IP
	 */
	private String lastUpdateOriginIp;
	
	/*
	 * 最后登录IP
	 */
	private String lastLoginIp;
	
	/*
	 *最后登录源应用 
	 */
	private String lastLoginOriginApp;
	
	
	/*
	 * 解锁时间
	 */
	private Date unlockedDate;
	
	/*
	 *是否被锁定 ，0被锁定，1没有
	 */
	private int isLocked;
	
	
	
	@SuppressWarnings("serial")
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list=new ArrayList<GrantedAuthority>();
			list.add(new GrantedAuthority() {
				@Override
				public String getAuthority() {
					return getUserType();
				}
			});
		return list;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.name;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isLocked==1?true:false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(int isLocked) {
		this.isLocked = isLocked;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getCreateOriginIp() {
		return createOriginIp;
	}

	public void setCreateOriginIp(String createOriginIp) {
		this.createOriginIp = createOriginIp;
	}

	public String getLastUpdateOriginApp() {
		return lastUpdateOriginApp;
	}

	public void setLastUpdateOriginApp(String lastUpdateOriginApp) {
		this.lastUpdateOriginApp = lastUpdateOriginApp;
	}

	public String getLastUpdateOriginIp() {
		return lastUpdateOriginIp;
	}

	public void setLastUpdateOriginIp(String lastUpdateOriginIp) {
		this.lastUpdateOriginIp = lastUpdateOriginIp;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getLastLoginOriginApp() {
		return lastLoginOriginApp;
	}

	public void setLastLoginOriginApp(String lastLoginOriginApp) {
		this.lastLoginOriginApp = lastLoginOriginApp;
	}

	public Date getUnlockedDate() {
		return unlockedDate;
	}

	public void setUnlockedDate(Date unlockedDate) {
		this.unlockedDate = unlockedDate;
	}
}