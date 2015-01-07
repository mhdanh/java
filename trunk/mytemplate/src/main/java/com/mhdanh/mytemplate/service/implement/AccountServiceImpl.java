package com.mhdanh.mytemplate.service.implement;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mhdanh.mytemplate.dao.AccountDao;
import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.domain.Account.ACCOUNT_STATUS;
import com.mhdanh.mytemplate.service.AccountService;
import com.mhdanh.mytemplate.service.MailService;
import com.mhdanh.mytemplate.utility.Utility;
import com.mhdanh.mytemplate.viewmodel.MailSenderDTO;

@Service
@Transactional
public class AccountServiceImpl extends CommonServiceImpl<Account> implements
		AccountService {

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
		for (int i = 4; i < 10; i++) {
			if (i != 9) {
				Account acc = new Account();
				acc.setUsername(String.valueOf(i));
				acc.setPassword("test");
				accountDao.save(acc);
			} else {
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

			if (!isExistAccountByEmail(registerAccount.getEmail())) {
				// save account into db
				registerAccount.setToken(utility
						.hashStringWithDefaultKey(registerAccount.getEmail()));
				registerAccount.setStatus(ACCOUNT_STATUS.WAITING);
				this.add(registerAccount);
			}

			String subjectRegister = utility
					.getMessage("msg.register.email.register.subject");
			String myui = utility.getMessage("msg.mydomain");
			HttpServletRequest request = (((ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes()).getRequest());
			String linkActive = request.getScheme()
					+ "://"
					+ request.getServerName()
					+ ":"
					+ request.getServerPort()
					+ request.getContextPath()
					+ "/init-account/"
					+ utility.hashStringWithDefaultKey(registerAccount
							.getEmail());
			String contentRegister = utility.getMessage(
					"msg.register.email.register.content",
					registerAccount.getEmail(), myui, linkActive);
			// send mail
			MailSenderDTO email = new MailSenderDTO();
			email.setTo(registerAccount.getEmail());
			email.setSubject(subjectRegister);
			email.setContent(contentRegister);
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

	private boolean isExistAccountByEmail(String email) {
		Account account = accountDao.getAccountByEmail(email);
		if (account != null) {
			return true;
		}
		return false;
	}

	@Override
	public String initAccountPage(String token) {
		Account accountByToken = accountDao.getAccountByToken(token);
		if (accountByToken == null) {
			return "/404";
		}
		return "/init-account/";
	}

	@Override
	public String updateInitAccount(Account accountTemp,String token) {
		Account accountByToken = accountDao.getAccountByToken(token);
		if (accountTemp.getUsername() != null
				&& accountTemp.getPassword() != null) {
			// update account
			accountByToken.setDateCreated(new Date());
			accountByToken.setDateModified(new Date());
			accountByToken.setUsername(accountTemp.getUsername());
			accountByToken.setPassword(accountTemp.getPassword());
			accountByToken.setFirstName(accountTemp.getFirstName());
			accountByToken.setLastName(accountTemp.getLastName());
			accountByToken.setStatus(ACCOUNT_STATUS.ACTIVE);
			accountDao.update(accountByToken);
		}
		return "/init-account/";
	}
}
