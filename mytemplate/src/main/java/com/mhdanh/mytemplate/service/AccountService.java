package com.mhdanh.mytemplate.service;

import org.springframework.ui.Model;

import com.mhdanh.mytemplate.domain.Account;

public interface AccountService extends CommonService<Account>{
	boolean existUsernameAndPasword(String username,String password);
	Account getAccountByUsername(String username);
	
	
	String registerAccount(Account registerAccount);
	String registerSuccessAccount(Model model, String token);
	String registerResendEmailAccount(Model model, String token);
	String initAccountPage(String token);
	String updateInitAccount(Account accountTemp,String token);
	String loginSystem();
	/**
	 * false:not exist
	 * true:exist
	 * @param email
	 * @return key:message
	 */
	Object ajaxRegisterCheckEmailExist(String email);
	String registerReportSendEmailNotSuccessfull(Model model, String token);
	/**
	 * 
	 * @param email
	 * @return key:notexist, msg: email not exist
	 * key:existbutnotactive,msg: this email exist but has't active, please this this link to active account.
	 * 
	 * 
	 */
	Object ajaxForgotPasswordCheckEmail(String email);
	String forgotPasswordUpdate(Model model, String email);
	String setNewPassword(Model model, String keyRecoverPassword);
	String updateNewPassword(Model model, String keyRecoverPassword,String password);
	String notRequestRecoverPassword(Model model, String keyRecoverPassword);
}
