package com.mhdanh.mytemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.service.AccountService;
import com.mhdanh.mytemplate.service.MailService;
import com.mhdanh.mytemplate.utility.Utility;
import com.mhdanh.mytemplate.viewmodel.MailSenderDTO;

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

}
