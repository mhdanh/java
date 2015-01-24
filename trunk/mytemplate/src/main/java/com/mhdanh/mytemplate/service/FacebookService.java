package com.mhdanh.mytemplate.service;

import javax.servlet.http.HttpSession;

public interface FacebookService {

	//---------
	String authFacebook(HttpSession session);
	String authFacebookCallBack(String code, String state, HttpSession session);
}
