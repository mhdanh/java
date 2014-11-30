package com.mhdanh.mytemplate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping(value = {"/login","/login/"})
	private String loginPage(){
		return "loginLoginPage";
	}
}
