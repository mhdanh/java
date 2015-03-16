package com.mhdanh.mytemplate.test.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.service.AccountService;
import com.mhdanh.mytemplate.service.RoleService;


public class AccountTest extends ConfigTest{

	@Autowired
	AccountService accountService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	SessionFactory sessionFactory;
	
	
	@Test
	public void test() {
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(sessionFactory.openSession()));
		List<Account> accounts = accountService.getAll();
		Assert.assertNotNull(accounts.get(0).getRoles());
	}
	
	@Test
	public void roleTest(){
		List<Account> accounts = accountService.getAll();
		Assert.assertNotNull(accounts);
	}
	
	@Test
	public void compareAccountTest(){
		Account account1 = new Account();
		account1.setId(1);
		account1.setUsername("account1");
		
		Account account2 = new Account();
		account2.setId(1);
		account2.setUsername("account2");
		
		Assert.assertEquals(true, account1.equals(account2));
	}
	
}
