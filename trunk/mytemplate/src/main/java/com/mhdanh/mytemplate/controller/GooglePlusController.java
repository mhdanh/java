package com.mhdanh.mytemplate.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.googlecode.googleplus.GooglePlusFactory;
import com.mhdanh.mytemplate.service.GooglePlusService;

@Controller
public class GooglePlusController {

	@Autowired
	private GooglePlusService googlePlusService;

	@Autowired
	GooglePlusFactory googlePlusFactory;

	@RequestMapping("/auth/google")
	public String authGoogle(HttpSession session) {
		return googlePlusService.authGoogle();
	}

	@RequestMapping("/auth/google/callback")
	public String authGoogleCallBack(
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "error", required = false) String error) {
		return googlePlusService.authGoogleCallBack(code, error);
	}
}
