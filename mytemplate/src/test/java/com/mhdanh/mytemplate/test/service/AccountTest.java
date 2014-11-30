package com.mhdanh.mytemplate.test.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.testng.annotations.Test;

import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.service.AccountService;


public class AccountTest extends ConfigTest{

	@Autowired
	AccountService accountService;
	
	@Autowired
	SessionFactory sessionFactory;
	
	
	@Test
	public void test() {
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(sessionFactory.openSession()));
		List<Account> accounts = accountService.getAll();
		for(Account acc:accounts){
			System.out.println(acc.getRoles().size());
		}
	}
	
	@Test
	public void roleTest(){
		List<Account> accounts = accountService.getAll();
		System.out.println(accounts.size());
	}
}
