package com.demo.springSecurity.permissionEvaluator;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.demo.springSecurity.domain.UserInfo;

/**
 *前段标签获取 permission 相关
 * 
 * 自定义permissionEvaluator
 * @author kyrin
 *
 */
@Component
public class MyPermissionEvaluator implements PermissionEvaluator{

	@Override
	public boolean hasPermission(Authentication authentication,
			Object targetDomainObject, Object permission) {
		if(authentication.isAuthenticated()){
			System.out.println(targetDomainObject+"-"+permission+"-"+authentication.getPrincipal());
			UserInfo user=(UserInfo) authentication.getPrincipal();
			if(user.getPermissions().contains(permission)){
				return true;
			}
		}
		return true;
	}

	@Override
	public boolean hasPermission(Authentication authentication,
			Serializable targetId, String targetType, Object permission) {
		System.out.println(authentication.getPrincipal()+"-"+targetId+"-"+targetType+"-"+permission);
		return true;
	}

}
