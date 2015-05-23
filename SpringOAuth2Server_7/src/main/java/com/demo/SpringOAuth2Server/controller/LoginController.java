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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@Autowired
	private ApprovalStore tokenApprovalStore;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("loginpage");
		return model;
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
		
		//获取client默认的通过的或拥有的scope，设置为true
		for(Approval app:approvals){
			if(scopes.contains(app.getScope())){
				scopeMap.put("scope."+app.getScope(),"true");
			}
		}
		
		model.addObject("scopeMap", scopeMap);
		model.addObject("client",authorizationRequest.getClientId());
		
		model.setViewName("confirm_access");
		
		return model;
	}
	
	
	
}
