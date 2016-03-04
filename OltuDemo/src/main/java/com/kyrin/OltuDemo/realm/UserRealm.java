package com.kyrin.OltuDemo.realm;


import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class UserRealm extends AuthorizingRealm{

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		List<String> roles=new ArrayList<String>();
		roles.add("ROLE_USER");
		SimpleAuthorizationInfo author=new SimpleAuthorizationInfo();
		author.addRoles(roles);
		return author;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		System.out.println("-----");
		String username = (String) token.getPrincipal();
		String password = (String) token.getCredentials();
		if("admin".equals(username)){
			SimpleAuthenticationInfo authen=new SimpleAuthenticationInfo(username,password,"");
			return authen;
		}
		return null;
	}

	 

	 
}
