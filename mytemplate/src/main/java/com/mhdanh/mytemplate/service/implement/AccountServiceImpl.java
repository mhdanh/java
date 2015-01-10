package com.mhdanh.mytemplate.service.implement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mhdanh.mytemplate.dao.AccountDao;
import com.mhdanh.mytemplate.dao.FeedbackDao;
import com.mhdanh.mytemplate.dao.RoleDao;
import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.domain.Account.ACCOUNT_STATUS;
import com.mhdanh.mytemplate.domain.Feedback;
import com.mhdanh.mytemplate.domain.Feedback.FEEDBACK_STATUS;
import com.mhdanh.mytemplate.domain.Feedback.FEEDBACK_TYPE;
import com.mhdanh.mytemplate.domain.Role;
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
	RoleDao roleDao;
	@Autowired
	FeedbackDao feedbackDao;
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
	public String registerAccount(Account registerAccount) {
		Account userLogined = utility.getUserLogined();
		if (userLogined != null) {
			return "redirect:/";
		}
		// send email to account
		if (registerAccount.getEmail() != null) {

			if (!isExistAccountByEmail(registerAccount.getEmail())) {
				//get role user
				List<Role> roles = new ArrayList<>();
				Role roleUser = roleDao.getRoleByRoleName(Role.ROLE_NAME.USER);
				roles.add(roleUser);
				// save account into db
				registerAccount.setToken(utility
						.hashStringWithDefaultKey(registerAccount.getEmail()));
				registerAccount.setStatus(ACCOUNT_STATUS.WAITING);
				registerAccount.setDateCreated(new Date());
				registerAccount.setRoles(roles);
				this.add(registerAccount);
			}else{
				Account accountByEmail = accountDao.getAccountByEmail(registerAccount.getEmail());
				if(accountByEmail.getStatus().equals(ACCOUNT_STATUS.ACTIVE)){
					return "/register";
				}
			}
			
			String tokenAccount = sendEmailActiveAccount(registerAccount);
			return "redirect:/register-success/"+tokenAccount;
		}
		return "/register";
	}

	private String sendEmailActiveAccount(Account registerAccount) {
		String subjectRegister = utility
				.getMessage("msg.register.email.register.subject");
		String myui = utility.getMessage("msg.mydomain");
		String tokenAccount = utility.hashStringWithDefaultKey(registerAccount
				.getEmail());
		String linkActive = utility.getUrlSystem()
				+ "/init-account/"
				+ tokenAccount;
		String contentRegister = utility.getMessage(
				"msg.register.email.register.content",
				registerAccount.getEmail(), myui, linkActive);
		// send mail
		MailSenderDTO email = new MailSenderDTO();
		email.setTo(registerAccount.getEmail());
		email.setSubject(subjectRegister);
		email.setContent(contentRegister);
		mailService.sendHtmlMail(email);
		return tokenAccount;
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
		if (accountByToken == null || accountByToken.getStatus().equals(ACCOUNT_STATUS.ACTIVE)) {
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
			accountByToken.setDateModified(new Date());
			accountByToken.setUsername(accountTemp.getUsername());
			accountByToken.setPassword(accountTemp.getPassword());
			accountByToken.setFirstName(accountTemp.getFirstName());
			accountByToken.setLastName(accountTemp.getLastName());
			accountByToken.setStatus(ACCOUNT_STATUS.ACTIVE);
			accountDao.update(accountByToken);
			return "redirect:/";
		}
		return "/init-account/";
	}


	@Override
	public String registerSuccessAccount(Model model, String token) {
		Account currentAccount = accountDao.getAccountByToken(token);
		if (currentAccount == null || currentAccount.getStatus().equals(ACCOUNT_STATUS.ACTIVE)) {
			return "/404";
		}
		String keyMessage = "msg.register.email.register.success.message.info";
		String linkResendMail = utility.getUrlSystem() + "/register-resend-email/"+token;
		String message = utility.getMessage(keyMessage, currentAccount.getEmail(),linkResendMail);
		model.addAttribute("message", message);
		return "/register-success";
	}

	@Override
	public String registerResendEmailAccount(Model model, String token) {
		Account currentAccount = accountDao.getAccountByToken(token);
		if (currentAccount == null || currentAccount.getStatus().equals(ACCOUNT_STATUS.ACTIVE)) {
			return "/404";
		}
		//send email
		String tokenAccount = sendEmailActiveAccount(currentAccount);
		String linkReportSendEmailNotSuccessFul = utility.getUrlSystem() + "/register-report-send-email-not-successful/"+token;
		String message = utility.getMessage("msg.register.resend.email.content",currentAccount.getEmail(),linkReportSendEmailNotSuccessFul);
		model.addAttribute("message", message);
		return "/register-resend-email";
	}

	@Override
	public Object ajaxRegisterCheckEmailExist(String email) {
		Map<String, String> mapResult = new HashMap<String, String>();
		Account accountByEmail = accountDao.getAccountByEmail(email);
		if(accountByEmail == null){
			//not exist
			mapResult.put("key", "false");
		}else{
			if(accountByEmail.getStatus().equals(ACCOUNT_STATUS.ACTIVE)) {
				mapResult.put("key", "true");
				mapResult.put("msg", utility.getMessage("msg.register.email.exist"));
			}else{
				mapResult.put("key", "false");
			}
		}
		return mapResult;
	}

	@Override
	public String registerReportSendEmailNotSuccessfull(Model model,
			String token) {
		Account accountByToken = accountDao.getAccountByToken(token);
		if (accountByToken == null || accountByToken.getStatus().equals(ACCOUNT_STATUS.ACTIVE)) {
			return "/404";
		}
		//send email notify
		String subject = utility.getMessage("msg.feedback.register.not.send.email.title");
		String content = utility.getMessage("msg.feedback.register.not.send.email.content",accountByToken.getEmail());
		
		Feedback errorFeedback = new Feedback();
		errorFeedback.setSubject(subject);
		errorFeedback.setContent(content);
		errorFeedback.setStatus(FEEDBACK_STATUS.UNREAD);
		errorFeedback.setType(FEEDBACK_TYPE.ERROR);
		errorFeedback.setDateCreated(new Date());
		
		feedbackDao.save(errorFeedback);
		
		return "/register-report-send-email-not-successful";
	}

	@Override
	public Object ajaxForgotPasswordCheckEmail(String email) {
		Map<String, String> mapResult = new HashMap<>();
		Account accountByEmail = accountDao.getAccountByEmail(email);
		if(accountByEmail == null){
			String messageNotExist = utility.getMessage("msg.forgot.password.email.notexist");
			mapResult.put("key", "notexist");
			mapResult.put("msg",messageNotExist);
		}else if(accountByEmail.getStatus().equals(ACCOUNT_STATUS.WAITING)){
			String linkResendEmail = utility.getUrlSystem() + "/register-resend-email/"+accountByEmail.getToken();
			String messageNotActive = utility.getMessage("msg.forgot.password.email.notactive",linkResendEmail);
			mapResult.put("key", "notactive");
			mapResult.put("msg",messageNotActive);
		}else if(accountByEmail.getStatus().equals(ACCOUNT_STATUS.BLOCKED)){
			String messageBlocked = utility.getMessage("msg.forgot.password.email.blocked");
			mapResult.put("key", "blocked");
			mapResult.put("msg",messageBlocked);
		}
		return mapResult;
	}

	@Override
	public String forgotPasswordUpdate(Model model, String email) {
		Account accountByEmail = accountDao.getAccountByEmail(email);
		if(accountByEmail != null && accountByEmail.getStatus().equals(ACCOUNT_STATUS.ACTIVE)){
			String currentTime = String.valueOf(new Date().getTime());
			
			String keyRecovePassword = utility.hashStringWithDefaultKey(currentTime);
			String linkSetNewPassword = utility.getUrlSystem() + "/set-new-password/" + keyRecovePassword;
			String linkNotRequireRecoverPassword = utility.getUrlSystem() + "/not-request-recover-password/" + keyRecovePassword;
			
			//update account
			accountByEmail.setKeyRecoverPassword(keyRecovePassword);
			accountDao.update(accountByEmail);
			//send email
			String subjectRecoverPassword = utility.getMessage("msg.forgot.password.title");
			String contentRecoverPassword = utility.getMessage("msg.forgot.password.recover.password.content",linkSetNewPassword,linkNotRequireRecoverPassword);
			MailSenderDTO emailSetNewPassword = new MailSenderDTO();
			emailSetNewPassword.setTo(email);
			emailSetNewPassword.setSubject(subjectRecoverPassword);
			emailSetNewPassword.setContent(contentRecoverPassword);
			mailService.sendHtmlMail(emailSetNewPassword);
			
			String messageInformUser = utility.getMessage("msg.send.email.recover.password.content",accountByEmail.getEmail());
			model.addAttribute("message", messageInformUser);
			return "/send-mail-for-recover-password";
		}
		return "/404";
	}

	@Override
	public String setNewPassword(Model model, String keyRecoverPassword) {
		Account accountByKeyRecoverPassword = accountDao.getAccountByKeyRecoverPassword(keyRecoverPassword);
		if(accountByKeyRecoverPassword == null || keyRecoverPassword.isEmpty()){
			return "/404";
		}
		model.addAttribute("keyRecoverPassword",keyRecoverPassword);
		return "/set-new-password";
	}

	@Override
	public String updateNewPassword(Model model, String keyRecoverPassword,String password) {
		Account accountByKeyRecoverPassword = accountDao.getAccountByKeyRecoverPassword(keyRecoverPassword);
		if(accountByKeyRecoverPassword == null || password.isEmpty()){
			return "/404";
		}
		accountByKeyRecoverPassword.setPassword(password);
		accountByKeyRecoverPassword.setKeyRecoverPassword(null);
		accountDao.update(accountByKeyRecoverPassword);
		return "redirect:/";
	}

	@Override
	public String notRequestRecoverPassword(Model model,
			String keyRecoverPassword) {
		Account accountByKeyRecoverPassword = accountDao.getAccountByKeyRecoverPassword(keyRecoverPassword);
		if(accountByKeyRecoverPassword == null){
			return "/404";
		}
		accountByKeyRecoverPassword.setKeyRecoverPassword(null);
		accountDao.update(accountByKeyRecoverPassword);
		return "/not-request-recover-password";
	}


}
