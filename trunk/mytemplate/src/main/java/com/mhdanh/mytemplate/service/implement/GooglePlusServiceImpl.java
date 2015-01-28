package com.mhdanh.mytemplate.service.implement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import com.googlecode.googleplus.GooglePlusFactory;
import com.googlecode.googleplus.Plus;
import com.googlecode.googleplus.model.person.Person;
import com.googlecode.googleplus.model.person.PersonEmails;
import com.mhdanh.mytemplate.dao.AccountDao;
import com.mhdanh.mytemplate.dao.RoleDao;
import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.domain.Role;
import com.mhdanh.mytemplate.domain.Account.ACCOUNT_STATUS;
import com.mhdanh.mytemplate.service.GooglePlusService;
import com.mhdanh.mytemplate.service.MailService;
import com.mhdanh.mytemplate.utility.Utility;
import com.mhdanh.mytemplate.viewmodel.MailSenderDTO;

@Service
public class GooglePlusServiceImpl implements GooglePlusService {

	@Autowired
	private GooglePlusFactory googlePlusFactory;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private Utility utility;
	@Autowired
	private MailService mailService;

	@Override
	public String authGoogle() {
		OAuth2Parameters oAuthParams = new OAuth2Parameters();
		oAuthParams
				.setRedirectUri(utility.getUrlSystem() + "/auth/google/callback");
		oAuthParams.setScope("https://www.googleapis.com/auth/userinfo.email");
		String url = googlePlusFactory
				.getOAuthOperations()
				.buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, oAuthParams);
		return "redirect:" + url;
	}

	@Override
	public String authGoogleCallBack(String code, String error) {
		if ("access_denied".equals(error)) {
			return "redirect:/login";
		}
		AccessGrant accessGrant = getAccessGrant(code);
		Plus plus = googlePlusFactory.getApi(accessGrant.getAccessToken());
		Person userProfile = plus.getPeopleOperations().get("me");
		List<PersonEmails> emails = userProfile.getEmails();
		String emailUser = "";
		if(!emails.isEmpty()){
			emailUser = emails.get(0).getValue();
		}
		if(!emailUser.isEmpty()){
			// check user exist or not
			Account accountFromEmail = accountDao.getAccountByEmail(emailUser);
			if (accountNotExistInSystem(accountFromEmail)) {
				authenticationGooglePlusWithAccountNotExistInSystem(userProfile);
			} else {
				// check account active or not
				if (isAccountFromEmailActive(accountFromEmail)) {
					// update first name and last name
					authenticationGooglePlusWithAccountActiveInSystem(userProfile,
							accountFromEmail);
				} else if (isAccountFromEmailWaitingInSystem(accountFromEmail)) {

					authenticationGooglePlusWithAccountWaitingInSystem(userProfile,
							accountFromEmail);
				}
			}
		}
		return "redirect:/";
	}

	private void authenticationGooglePlusWithAccountWaitingInSystem(
			Person userProfile, Account accountFromEmail) {
		
		String emailFromGooglePlus = userProfile.getEmails().get(0).getValue();
		String newPassword = utility.generatePassword();
		accountFromEmail.setDateModified(new Date());
		accountFromEmail.setUsername(emailFromGooglePlus);
		accountFromEmail.setFirstName("");
		accountFromEmail.setLastName(userProfile.getDisplayName());
		accountFromEmail.setStatus(ACCOUNT_STATUS.ACTIVE);
		accountFromEmail.setPassword(utility
				.hashStringWithDefaultKey(newPassword));
		accountDao.update(accountFromEmail);
		List<GrantedAuthority> authorities = new ArrayList<>();
		List<Role> rolesOfCurrentAccount = accountFromEmail.getRoles();
		for (Role r : rolesOfCurrentAccount) {
			authorities.add(new GrantedAuthorityImpl(r.getName()));
		}
		Authentication auth = new UsernamePasswordAuthenticationToken(
				emailFromGooglePlus, "", authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);
		notifyEmailAutoGeneratePasswordOfGooglePlusAccount(userProfile, newPassword);
	}

	private boolean isAccountFromEmailWaitingInSystem(Account accountFromEmail) {
		return accountFromEmail.getStatus().equals(
				Account.ACCOUNT_STATUS.WAITING);
	}

	private void authenticationGooglePlusWithAccountActiveInSystem(
			Person userProfile, Account accountFromEmail) {
		
		accountFromEmail.setFirstName("");
		accountFromEmail.setLastName(userProfile.getDisplayName());
		accountDao.update(accountFromEmail);
		// authentication for exist account
		List<GrantedAuthority> authorities = new ArrayList<>();
		List<Role> rolesOfCurrentAccount = accountFromEmail.getRoles();
		for (Role r : rolesOfCurrentAccount) {
			authorities.add(new GrantedAuthorityImpl(r.getName()));
		}
		Authentication auth = new UsernamePasswordAuthenticationToken(
				accountFromEmail.getUsername(), "", authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	private boolean isAccountFromEmailActive(Account accountFromEmail) {
		return accountFromEmail.getStatus().equals(
				Account.ACCOUNT_STATUS.ACTIVE);
	}

	private void authenticationGooglePlusWithAccountNotExistInSystem(
			Person userProfile) {
		
		String emailFromGooglePlus = userProfile.getEmails().get(0).getValue();
		String newPassword = utility.generatePassword();
		// insert new account with username and email is the same
		Account createAccountFromGooglePlus = new Account();
		createAccountFromGooglePlus.setDateCreated(new Date());
		createAccountFromGooglePlus.setDateModified(new Date());
		createAccountFromGooglePlus.setUsername(emailFromGooglePlus);
		createAccountFromGooglePlus.setFirstName("");
		createAccountFromGooglePlus.setLastName(userProfile.getDisplayName());
		createAccountFromGooglePlus.setEmail(emailFromGooglePlus);
		createAccountFromGooglePlus.setStatus(ACCOUNT_STATUS.ACTIVE);
		createAccountFromGooglePlus.setPassword(utility
				.hashStringWithDefaultKey(newPassword));

		List<Role> userRoles = new ArrayList<>();
		userRoles.add(roleDao.getRoleByRoleName(Role.ROLE_NAME.USER));
		createAccountFromGooglePlus.setRoles(userRoles);
		accountDao.save(createAccountFromGooglePlus);

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new GrantedAuthorityImpl(String
				.valueOf(Role.ROLE_NAME.USER)));
		Authentication auth = new UsernamePasswordAuthenticationToken(
				emailFromGooglePlus, "", authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);

		notifyEmailAutoGeneratePasswordOfGooglePlusAccount(userProfile, newPassword);
	}

	private void notifyEmailAutoGeneratePasswordOfGooglePlusAccount(Person userProfile,
			String newPassword) {
		String userFromGooglePlus = userProfile.getEmails().get(0).getValue();
		// auto generate password and send email to user
		MailSenderDTO mailGeneratePassword = new MailSenderDTO();
		mailGeneratePassword.setContent(utility.getMessage(
				"msg.login.googleplus.generate.password", userProfile.getDisplayName(),
				userFromGooglePlus, newPassword));
		mailGeneratePassword.setSubject(utility
				.getMessage("msg.login.auto.generate.password.subject"));
		mailGeneratePassword.setTo(userFromGooglePlus);
		mailService.sendHtmlMail(mailGeneratePassword);
	}
	
	private boolean accountNotExistInSystem(Account accountFromEmail) {
		return accountFromEmail == null;
	}

	private AccessGrant getAccessGrant(String authorizationCode) {
		OAuth2Operations oauthOperations = googlePlusFactory
				.getOAuthOperations();
		return oauthOperations.exchangeForAccess(authorizationCode,
				"http://localhost:8080/auth/google/callback", null);
	}
}
