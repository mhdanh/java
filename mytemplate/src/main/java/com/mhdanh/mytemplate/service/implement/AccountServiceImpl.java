package com.mhdanh.mytemplate.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mhdanh.mytemplate.dao.AccountDao;
import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.service.AccountService;

@Service
public class AccountServiceImpl extends CommonServiceImpl<Account> implements AccountService{
	
	@Autowired
	AccountDao accountDao;
	
	@Override
	public boolean existUsernameAndPasword(String username, String password) {
		return accountDao.existUsernameAndPasword(username, password);
	}

	@Override
	public Account getAccountByUsername(String username) {
		return accountDao.getAccountByUsername(username);
	}

}
