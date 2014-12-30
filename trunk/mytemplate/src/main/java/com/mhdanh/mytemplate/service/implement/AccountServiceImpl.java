package com.mhdanh.mytemplate.service.implement;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mhdanh.mytemplate.dao.AccountDao;
import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.service.AccountService;

@Service
@Transactional
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

}
