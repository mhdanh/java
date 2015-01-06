package com.mhdanh.mytemplate.service;

import com.mhdanh.mytemplate.domain.Account;

public interface AccountService extends CommonService<Account>{
	boolean existUsernameAndPasword(String username,String password);
	Account getAccountByUsername(String username);
	
	
	String registerAccount(Account registerAccount);
	String loginSystem();
	void testtransaction();
}
