package com.mhdanh.mytemplate.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.utility.Utility;

@Controller
public class LoginController {
	
	@Autowired
	Utility utility;
	
	@RequestMapping(value = {"/login","/login/"})
	private String loginPage(HttpServletRequest request){
		Account userLogined = utility.getUserLogined();
		if(userLogined != null){
			 return "redirect:/";
		}
		return "/login";
	}
}
