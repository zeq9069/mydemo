package com.kyrin.OltuDemo.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError.TokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kyrin.OltuDemo.constant.GlobalConstant;
import com.kyrin.OltuDemo.service.OAuthService;
import com.kyrin.OltuDemo.utils.DateUtil;

/**
 * access Token controller
 * 
 * @author zhangerqiang
 *
 */
@RestController
public class AccessTokenController {

	@Autowired
	private OAuthService oauthService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/token")
	public HttpEntity accessToken(HttpServletRequest request) throws OAuthSystemException, UnsupportedEncodingException{

		try{
		 OAuthTokenRequest oauthRequest=new OAuthTokenRequest(request);
		 //执行校验
		 HttpEntity validateResult = validate(oauthRequest);
		if(validateResult!=null){
			return validateResult;
		}
		//验证授权码
		 String username=oauthService.getAuthorzationCode(oauthRequest.getCode());
		 if(username==null){
			 OAuthResponse resp=OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
					 	.setErrorDescription(GlobalConstant.INVALIDATE_GRANT_CODE_EXCEPTION)
					 		.setError(TokenResponse.INVALID_GRANT).buildBodyMessage();
					 return new ResponseEntity(resp.getBody(), HttpStatus.valueOf(resp.getResponseStatus()));
		 }
		 OAuthIssuerImpl oauthIssuerImpl=new OAuthIssuerImpl(new MD5Generator());
		 String accessToken=oauthIssuerImpl.accessToken();
		 String refreshToken=oauthIssuerImpl.refreshToken();
		 Date expireIn=new Date();
		 oauthService.saveAccessToken(accessToken, accessToken, oauthRequest.getClientId(), username, DateUtil.plus(expireIn, GlobalConstant.EXPIRE, TimeUnit.SECONDS));
		 OAuthResponse oauthResponse=OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
				 .setAccessToken(accessToken)
				 .setExpiresIn(GlobalConstant.EXPIRE+"")
				 .setRefreshToken(refreshToken)
				 .setScope("info")
				 .buildJSONMessage();
		 return new ResponseEntity(oauthResponse.getBody(), HttpStatus.valueOf(oauthResponse.getResponseStatus()));
		}catch(OAuthProblemException e){
			//构建错误响应
            OAuthResponse res = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e)
                    .buildJSONMessage();
            return new ResponseEntity(res.getBody(), HttpStatus.valueOf(res.getResponseStatus()));
		}
	}
	
	@SuppressWarnings({ "rawtypes"})
	public HttpEntity validate(OAuthTokenRequest oauthRequest) throws OAuthSystemException{
		 //验证client_id和client_secret
		 if(!oauthService.checkClientId(oauthRequest.getClientId()) || 
				 !oauthService.checkClientSecret(oauthRequest.getClientId(), oauthRequest.getClientSecret())){
			 return  createResponseEntity(HttpServletResponse.SC_BAD_REQUEST, GlobalConstant.INVALIDATE_CLIENT_EXCEPTION, 
					 TokenResponse.INVALID_CLIENT);
		 }
		 //验证client_secret
		 if(!oauthService.checkClientSecret(oauthRequest.getClientId(), oauthRequest.getClientSecret())){
			 	return  createResponseEntity(HttpServletResponse.SC_UNAUTHORIZED, GlobalConstant.INVALIDATE_CLIENT_EXCEPTION, 
						 TokenResponse.INVALID_CLIENT);
		 }
		 //验证redirect_uri
		 if(oauthService.checkRedirectUrl(oauthRequest.getClientId(), oauthRequest.getClientSecret())){
				return  createResponseEntity(HttpServletResponse.SC_NOT_FOUND, GlobalConstant.INVALIDATE_REQUEST_REDIRECT_URI_EXCEPTION, 
							 TokenResponse.INVALID_REQUEST);
		 }
		 //验证授权类型，目前支持authorization_code
		 if(!oauthRequest.getGrantType().equals(GrantType.AUTHORIZATION_CODE.toString())){
			 	return createResponseEntity(HttpServletResponse.SC_NOT_FOUND, GlobalConstant.INVALIDATE_GRANT_TYPE_EXCEPTION, 
					TokenResponse.INVALID_GRANT);
		 }
		 return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ResponseEntity createResponseEntity(int errorResponse,String errorDescription,String error) throws OAuthSystemException{
		 OAuthResponse resp=OAuthASResponse.errorResponse(errorResponse)
				 	.setErrorDescription(errorDescription)
				 		.setError(error).buildBodyMessage();
				 return new ResponseEntity(resp.getBody(), HttpStatus.valueOf(resp.getResponseStatus()));
	}
}
