package com.demo.SpringOAuth2Server.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OauthAccessController {

	@Autowired
	private ApprovalStore tokenApprovalStore;
	
	@RequestMapping("/oauth/error")
	public ModelAndView handleError(Map<String,Object> model){
		ModelAndView mv=new ModelAndView();
		mv.addObject("message", "There was a problem with the OAuth2 protocol");
		mv.setViewName("oauth/error");
		return mv;
	}
	
	@RequestMapping(value="/oauth/confirm_access")
	public ModelAndView confirm(HttpSession session,ModelAndView model){
		AuthorizationRequest authorizationRequest=(AuthorizationRequest) session.getAttribute("authorizationRequest");
		Collection<Approval> approvals=tokenApprovalStore.getApprovals(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), authorizationRequest.getClientId());
		Map<String,String> scopeMap=new HashMap<String, String>();
		Set<String> scopes=authorizationRequest.getScope();
		
		//获取第三方请求的scope
		for(String scope:scopes){
			scopeMap.put("scope."+scope,"false");
		}
		
		//获取client默认限定的scope，设置为true
		for(Approval app:approvals){
			if(scopes.contains(app.getScope())){
				scopeMap.put("scope."+app.getScope(),"true");
			}
		}
		
		model.addObject("scopeMap", scopeMap);
		model.addObject("client",authorizationRequest.getClientId());
		
		model.setViewName("oauth/confirm_access");
		
		return model;
	}
	
	
}
