package com.demo.SpringOAuth2Server.saltSource;

import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 自定义saltSource,让每个用户的username的值当做本用户的salt
 * @author zhangerqiang
 *
 */
@Component
public class MySaltSource implements SaltSource{

	@Override
	public Object getSalt(UserDetails user) {
		return user.getUsername();
	}

}
