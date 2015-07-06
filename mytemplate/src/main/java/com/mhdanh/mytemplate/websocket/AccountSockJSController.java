package com.mhdanh.mytemplate.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.mhdanh.mytemplate.dao.AccountDao;

@Controller
public class AccountSockJSController {
	
	@Autowired
	AccountDao accountDao;
	
	@MessageMapping("/add-account")
	@SendTo("/state/account")
	public Greeting addAccount(HelloMessage message) {
		System.out.println("account name: " + message.getName());
		return new Greeting("Create account success " + message.getName() + " account size " + accountDao.getAll().size());
	}
	
}
