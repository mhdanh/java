package com.mhdanh.mytemplate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.service.AccountService;

@Controller
@RequestMapping("/admin")
public class ManageAccountRoleController {
	
	@Autowired
	AccountService accountService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping("/account")
	@ResponseBody
	private void account(){
		List<Account> accounts = accountService.getAll();
		for(Account acc:accounts){
			System.out.println(acc.getRoles().size());
		}
	}

}
