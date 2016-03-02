package com.kyrin.CAS.controller.config;

import java.util.Collections;
import java.util.Set;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MyCASRealm extends CasRealm{


	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String p=(String) principals.getPrimaryPrincipal();
		System.out.println(p);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Set<String> roles=Collections.emptySet();
		roles.add("ROLE_USER");
		info.setRoles(roles);
		return info;
	}
	
	
}
