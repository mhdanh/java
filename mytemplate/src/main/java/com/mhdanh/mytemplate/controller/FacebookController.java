package com.mhdanh.mytemplate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mhdanh.mytemplate.utility.Utility;

@Controller
public class FacebookController {

	@Autowired
	Utility utility;
	
	@RequestMapping("/facebook/init")
	@ResponseBody
	private Object initFacebook(){
		String tokenFace = utility.getValueFromPropertiesSystemFile("system.facebook.token");
		Facebook facebook = new FacebookTemplate(tokenFace);
		facebook.feedOperations().updateStatus("ta la ai test.");
		
		return "ok man";
	}
	
}
