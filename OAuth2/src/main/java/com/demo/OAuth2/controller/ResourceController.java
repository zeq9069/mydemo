package com.demo.OAuth2.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ***********************
 *    OAuth2.0
 *    
 *    资源控制器
 *
 *http://localhost:8080/OAuth2/account?access_token=828beda907066d058584f37bcfd597b6
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年3月30日]
 *
 */
@Controller
public class ResourceController {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/account")
	@ResponseBody
	public String userInfo(HttpServletRequest request){
		try {
			
			//1,构造 resource request
			OAuthAccessResourceRequest accessRequest=new OAuthAccessResourceRequest(request,ParameterStyle.QUERY);
			//获取accessToken
			String accessToken=accessRequest.getAccessToken();
			System.out.print("accessToken="+accessToken);
			
			//从内存中获取校验是否过期
			
			return (new ResponseEntity("kyrin",HttpStatus.OK)).getBody().toString();
			
		} catch (OAuthSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthProblemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";
	}
	
}
