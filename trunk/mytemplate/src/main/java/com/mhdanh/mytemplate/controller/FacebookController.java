package com.mhdanh.mytemplate.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mhdanh.mytemplate.service.FacebookService;
import com.mhdanh.mytemplate.utility.Utility;

@Controller
public class FacebookController {

	@Autowired
	Utility utility;
	@Autowired
	FacebookService facebookService;

	@RequestMapping("/facebook/init")
	@ResponseBody
	private Object initFacebook() {
		String tokenFace = utility
				.getValueFromPropertiesSystemFile("system.facebook.token");
		Facebook facebook = new FacebookTemplate(tokenFace);
		facebook.feedOperations().updateStatus("ta la ai test.");
		return "ok man";
	}

	@RequestMapping("/auth/facebook")
	public String authFacebook(HttpSession session) {
		return facebookService.authFacebook(session);
	}

	@RequestMapping("/auth/facebook/callback")
	public String authFacebookCallBack(@RequestParam("code") String code,
			@RequestParam("state") String state, HttpSession session) {
		return facebookService.authFacebookCallBack(code, state, session);
	}
}
