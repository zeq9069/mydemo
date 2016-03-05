package com.kyrin.OltuDemo.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kyrin.OltuDemo.service.OAuthService;

@Controller
public class AuthorizeController {

	private final static String INVALID_CLIENT_DESCRIPTION = "error client_id";

	private final static String INVALID_REDIRECT_URL_DESCRIPTION = "error redirect_url";

	@Autowired
	public OAuthService oauthService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/authorize")
	public Object authorize(HttpServletRequest request,
			HttpServletResponse response, Model model)
			throws OAuthSystemException, URISyntaxException {

		try {
			OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
			OAuthResponse oauthResponse = null;
			if (!oauthService.checkClientId(oauthRequest.getClientId())) {
				oauthResponse = OAuthASResponse
						.errorResponse(HttpServletResponse.SC_NOT_FOUND)
						.setError(OAuthError.TokenResponse.INVALID_CLIENT)
						.setErrorDescription(INVALID_CLIENT_DESCRIPTION)
						.buildBodyMessage();
				return new ResponseEntity(oauthResponse.getBody(),
						HttpStatus.valueOf(oauthResponse.getResponseStatus()));
			}
			if (!oauthService.checkRedirectUrl(oauthRequest.getClientId(),
					oauthRequest.getRedirectURI())) {
				oauthResponse = OAuthASResponse
						.errorResponse(HttpServletResponse.SC_NOT_FOUND)
						.setError(OAuthError.TokenResponse.INVALID_REQUEST)
						.setErrorDescription(INVALID_REDIRECT_URL_DESCRIPTION)
						.buildBodyMessage();
				return new ResponseEntity(oauthResponse.getBody(),
						HttpStatus.valueOf(oauthResponse.getResponseStatus()));
			}

			// 登录验证
			Subject currentUser = SecurityUtils.getSubject();
			if (!currentUser.isAuthenticated()) {
				if (!login(currentUser, request)) {// 登录失败，跳转到oauth_login
					model.addAttribute("client", oauthService
							.getClientInfo(oauthRequest.getClientId()));
					return "oauth_login";
				}
			}

			// 生成授权码，并将授权码缓存起来
			OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(
					new MD5Generator());
			String authorizationCode = oauthIssuerImpl.authorizationCode();
			String redirect_url = oauthRequest.getRedirectURI();

			// build OAuth response
			OAuthResponse resp = OAuthASResponse
					.authorizationResponse(request,
							HttpServletResponse.SC_FOUND)
					.setCode(authorizationCode).location(redirect_url)
					.buildQueryMessage();
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(new URI(resp.getLocationUri().toString()));
			oauthService.saveAuthorizationCode(authorizationCode, currentUser
					.getPrincipal().toString());
			return new ResponseEntity(headers, HttpStatus.valueOf(resp
					.getResponseStatus()));
		} catch (OAuthProblemException ex) {
			ex.printStackTrace();
			String redirect_url = ex.getRedirectUri();
			if (OAuthUtils.isEmpty(redirect_url)) {
				return new ResponseEntity<String>(
						"OAuth callback url need to be provided by client !",
						HttpStatus.NOT_FOUND);
			}

			final OAuthResponse resp = OAuthASResponse
					.errorResponse(HttpServletResponse.SC_FOUND).error(ex)
					.location(ex.getRedirectUri()).buildQueryMessage();
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(new URI(resp.getLocationUri().toString()));
			return new ResponseEntity(headers, HttpStatus.valueOf(resp
					.getResponseStatus()));
		}
	}

	private boolean login(Subject currentUser, HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return false;
		}
		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password);
		try {
			currentUser.login(token);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "登录失败：" + e.getClass().getName());
			return false;
		}
	}

}
