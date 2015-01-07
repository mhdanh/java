package com.mhdanh.mytemplate.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.service.AccountService;

@Controller
public class AccountController {

	@Autowired
	AccountService accountService;

	@RequestMapping(value = { "/login", "/login/" })
	private String loginPage() {
		return accountService.loginSystem();
	}

	@RequestMapping(value = { "/register", "/register/" })
	private String registerPage(
			@ModelAttribute("register") Account registerAccount) {
		return accountService.registerAccount(registerAccount);
	}

	@RequestMapping(value = { "/init-account/{token}"})
	private String initAccountPage(Model model,@PathVariable("token") String token) {
		model.addAttribute("paramToken", token);
		return accountService.initAccountPage(token);
	}
	
	@RequestMapping(value = { "/init-account-update/{token}"})
	private String updateInitAccountPage(Model model,
			@PathVariable("token") String token,
			@ModelAttribute("initaccount") Account account) {
		return accountService.updateInitAccount(account,token);
	}

}
