package com.demo.OAuth2.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ***************************************************************
 *   OAuth2.0访问令牌控制器
 * 
 *
 *http://localhost:8080/OAuth2/accessToken，POST提交如下数据：
 *client_id= c1ebe466-1cdc-4bd3-ab69-77c3561b9dee  
 *& client_secret= d8346ea2-6017-43ed-ad68-19c0f971738b
 *&grant_type=authorization_code
 *&code=828beda907066d058584f37bcfd597b6
 *&redirect_uri=http://localhost:8090/client/oauth2-login
 *
 *  说一下client_id和client_secret这两个参数：
 *    这两个参数是你的网站向资源服务器进行申请的，成功共之后，会返回这两个参数，
 * 这样才认为你你访问人家的资源才是合法的
 *
 *
 *  accessToken和用户要对应起来，放入缓存，code也要和用户对应起来放入缓存
 *
 * ****************************************************************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年3月30日]
 *
 */
@Controller
public class AccessTokenController {
	
	@RequestMapping(value="/accessToken",method=RequestMethod.POST)
	@ResponseBody
	public String accessToken(HttpServletRequest request){
		
		try {
			//1,构造 token request
			OAuthTokenRequest tokenRequest=new OAuthTokenRequest(request);
			
			String client_id=tokenRequest.getClientId();
			String client_secret=tokenRequest.getClientSecret();
			String grant_type=tokenRequest.getGrantType();
			String code=tokenRequest.getCode();
			String redirect_url=tokenRequest.getRedirectURI();
			System.out.println("client_id="+client_id+"\n"+"client_secret="+client_secret+"\n"+"grant_type="+grant_type+"\n"+"code="+code+"\n"+"redisrect_url="+redirect_url);
			//期间需要各种验证和保存到缓存
			
			
			OAuthIssuer issuer=new OAuthIssuerImpl(new MD5Generator()); 
			final String accessToken=issuer.accessToken();
			OAuthResponse oauthResponse=OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
					.setExpiresIn(String.valueOf(30)).setAccessToken(accessToken).buildJSONMessage();
			System.out.println(oauthResponse.getBody()+"\n"+oauthResponse.getResponseStatus());
			// return new ResponseEntity(oauthResponse.getBody(), HttpStatus.valueOf(oauthResponse.getResponseStatus()));  
			return "accessToken="+accessToken;
		} catch (OAuthSystemException e) {
			e.printStackTrace();
		} catch (OAuthProblemException e) {
			e.printStackTrace();
		}
		return "error";
	}

}
