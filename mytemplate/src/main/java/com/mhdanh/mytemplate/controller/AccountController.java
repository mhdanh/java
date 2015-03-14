package com.mhdanh.mytemplate.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.service.AccountService;

@Controller
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = { "/login", "/login/" })
	public String loginPage() {
		return accountService.loginSystem();
	}
	
	@RequestMapping(value = { "/register", "/register/" })
	public String registerPage(
			@ModelAttribute("register") Account registerAccount) {
		return accountService.registerAccount(registerAccount);
	}
	
	@RequestMapping(value = { "/ajax-register-check-email-exist"},method = RequestMethod.POST)
	@ResponseBody
	public Object ajaxRegisterCheckEmailExist(@RequestParam("email") String email) {
		return accountService.ajaxRegisterCheckEmailExist(email);
	}
	
	@RequestMapping(value = { "/register-success/{token}"})
	public String registerSuccessPage(Model model,@PathVariable("token") String token) {
		return accountService.registerSuccessAccount(model,token);
	}
	
	
	@RequestMapping(value = { "/register-resend-email/{token}"})
	public String registerResendEmailPage(Model model,@PathVariable("token") String token) {
		return accountService.registerResendEmailAccount(model,token);
	}

	@RequestMapping(value = { "/register-report-send-email-not-successful/{token}"})
	public String registerReportSendEmailNotSuccessfull(Model model,@PathVariable("token") String token) {
		return accountService.registerReportSendEmailNotSuccessfull(model,token);
	}

	@RequestMapping(value = { "/init-account/{token}"})
	public String initAccountPage(Model model,@PathVariable("token") String token) {
		model.addAttribute("paramToken", token);
		return accountService.initAccountPage(token);
	}
	
	@RequestMapping(value = { "/init-account-update/{token}"})
	public String updateInitAccountPage(Model model,
			@PathVariable("token") String token,
			@ModelAttribute("initaccount") Account account) {
		return accountService.updateInitAccount(account,token);
	}

	@RequestMapping(value = { "/forgot-password"})
	public String forgotPasswordPage(Model model) {
		return "/forgot-password";
	}
	
	@RequestMapping(value = { "/ajax-forgot-password-check-email"}, method = RequestMethod.POST)
	@ResponseBody
	public Object ajaxForgotPasswordCheckEmail(@RequestParam("email") String email) {
		return accountService.ajaxForgotPasswordCheckEmail(email);
	}

	@RequestMapping(value = { "/send-mail-for-recover-password"})
	public String forgotPasswordUpdate(Model model,@RequestParam("email") String email) {
		return accountService.forgotPasswordUpdate(model,email);
	}

	@RequestMapping(value = { "/set-new-password/{keyrecoverpassword}"})
	public String setNewPassword(Model model,@PathVariable("keyrecoverpassword") String keyRecoverPassword) {
		return accountService.setNewPassword(model,keyRecoverPassword);
	}

	@RequestMapping(value = { "/update-new-password/{keyrecoverpassword}"})
	public String updateNewPassword(Model model,@PathVariable("keyrecoverpassword") String keyRecoverPassword,@RequestParam("password") String password) {
		return accountService.updateNewPassword(model,keyRecoverPassword,password);
	}
	
	@RequestMapping(value = { "/not-request-recover-password/{keyrecoverpassword}"})
	public String notRequestRecoverPassword(Model model,@PathVariable("keyrecoverpassword") String keyRecoverPassword) {
		return accountService.notRequestRecoverPassword(model,keyRecoverPassword);
	}
	
}
