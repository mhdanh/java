package com.mhdanh.mytemplate.service.implement;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mhdanh.mytemplate.dao.AccountDao;
import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.service.AccountService;
import com.mhdanh.mytemplate.service.MailService;
import com.mhdanh.mytemplate.utility.Utility;
import com.mhdanh.mytemplate.viewmodel.MailSenderDTO;

@Service
@Transactional
public class AccountServiceImpl extends CommonServiceImpl<Account> implements AccountService{
	
	@Autowired
	AccountDao accountDao;
	@Autowired
	Utility utility;
	@Autowired
	MailService mailService;
	
	@Override
	public boolean existUsernameAndPasword(String username, String password) {
		return accountDao.existUsernameAndPasword(username, password);
	}

	@Override
	public Account getAccountByUsername(String username) {
		return accountDao.getAccountByUsername(username);
	}

	@Override
	public void testtransaction() {
		for(int i = 4;i<10;i++){
			if(i != 9){
				Account acc = new Account();
				acc.setUsername(String.valueOf(i));
				acc.setPassword("test");
				accountDao.save(acc);
			}else{
				Account acc = new Account();
				acc.setUsername("4");
				acc.setPassword("test");
				accountDao.save(acc);
			}
			
		}
		
	}

	@Override
	public String registerAccount(Account registerAccount) {
		Account userLogined = utility.getUserLogined();
		if (userLogined != null) {
			return "redirect:/";
		}
		// send email to account
		if (registerAccount.getEmail() != null) {
			MailSenderDTO email = new MailSenderDTO();
			email.setTo(registerAccount.getEmail());
			email.setSubject("Register account myui.info");
			email.setContent("thank for register accoutn at <b>myui.info</b>");
			mailService.sendHtmlMail(email);
		}
		return "/register";
	}

	@Override
	public String loginSystem() {
		Account userLogined = utility.getUserLogined();
		if (userLogined != null) {
			return "redirect:/";
		}
		return "/login";
		
	}

}
