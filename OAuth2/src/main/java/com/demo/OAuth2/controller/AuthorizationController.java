package com.demo.OAuth2.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.as.response.OAuthASResponse.OAuthAuthorizationResponseBuilder;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ***********************
 *   oauth 授权页面
 * 
 * http://localhost:8080/OAuth2/authorize?client_id=c1ebe466-1cdc-4bd3-ab69-77c3561b9dee&response_type=code&redirect_uri=http://localhost:9080/client/oauth2-login
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年3月30日]
 *
 */

@Controller
public class AuthorizationController {
	
	@RequestMapping("/authorize")
	@ResponseBody
	public String authorization(HttpServletRequest request,HttpServletResponse response){
		try {
			//1,构造 OAuth request
			OAuthAuthzRequest oauthRequest=new OAuthAuthzRequest(request);
			//2,构造 OAuth response
			OAuthAuthorizationResponseBuilder builder=OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
			//获取请求路径中的response_type这个请求参数
			String response_type=oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
			//定义授权码
			String authorizationCode="";
			if(response_type.equals(ResponseType.CODE.toString())){
				OAuthIssuerImpl issuer=new OAuthIssuerImpl(new MD5Generator());
				authorizationCode=issuer.authorizationCode();//获取授权码
				System.out.println("授权码为：code="+authorizationCode);
			}
			
			if(response_type.equals(ResponseType.TOKEN.toString())){
				OAuthIssuerImpl issuer=new OAuthIssuerImpl(new MD5Generator());
				authorizationCode=issuer.authorizationCode();//获取授权码
				System.out.println("授权码为：token="+authorizationCode);
			}
			//设置到response
			builder.setCode(authorizationCode);
			//获取请求路径中的redirectURL
			String redirectURL=oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
			//设置redirectURL到reponse中
			OAuthResponse oauthResponse=builder.location(redirectURL).buildQueryMessage();
			
			//3,重定向
			//response.sendRedirect(oauthResponse.getLocationUri());
			return "重定向的URL为："+oauthResponse.getLocationUri();
		} catch (OAuthSystemException e) {
			e.printStackTrace();
		} catch (OAuthProblemException e) {
			e.printStackTrace();
		} 
		return "error";
	}

}
