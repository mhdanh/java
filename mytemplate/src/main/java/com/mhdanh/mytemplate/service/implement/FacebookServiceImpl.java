package com.mhdanh.mytemplate.service.implement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import com.mhdanh.mytemplate.dao.AccountDao;
import com.mhdanh.mytemplate.dao.RoleDao;
import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.domain.Account.ACCOUNT_STATUS;
import com.mhdanh.mytemplate.domain.Role;
import com.mhdanh.mytemplate.service.FacebookService;
import com.mhdanh.mytemplate.service.MailService;
import com.mhdanh.mytemplate.utility.Utility;
import com.mhdanh.mytemplate.viewmodel.MailSenderDTO;

@Service
@Transactional
public class FacebookServiceImpl implements FacebookService {

	private static final String STATE = "state";

	@Autowired
	private FacebookConnectionFactory facebookConnectionFactory;
	@Autowired
	private Utility utility;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MailService mailService;

	@Override
	public String authFacebook(HttpSession session) {
		String state = UUID.randomUUID().toString();
		session.setAttribute(STATE, state);

		OAuth2Operations oauthOperations = facebookConnectionFactory
				.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setRedirectUri(utility.getUrlSystem() + "/auth/facebook/callback");
		params.setState(state);
		params.setScope("email");

		String authorizeUrl = oauthOperations.buildAuthorizeUrl(
				GrantType.AUTHORIZATION_CODE, params);
		return "redirect:" + authorizeUrl;
	}

	@Override
	public String authFacebookCallBack(String code, String state,
			HttpSession session) {
		String stateFromSession = (String) session.getAttribute(STATE);
		session.removeAttribute(STATE);

		if (!state.equals(stateFromSession)) {
			return "redirect:/login";
		}

		AccessGrant accessGrant = getAccessGrant(code);
		UserProfile userProfile = getuserProfile(accessGrant);

		// check user allow to get email or not
		if (userFacebookNotAcceptSystemGetEmail(userProfile)) {
			return "redirect:/login";
		}

		String facebookUserId = getFacebookUserId(accessGrant);
		session.setAttribute("facebookUserId", facebookUserId);

		// check user exist or not
		Account accountFromEmail = accountDao.getAccountByEmail(userProfile
				.getEmail());

		if (accountNotExistInSystem(accountFromEmail)) {
			authenticationFacebookWithAccountNotExistInSystem(userProfile);
		} else {
			// check account active or not
			if (isAccountFromEmailActive(accountFromEmail)) {
				// update first name and last name
				authenticationFacebookWithAccountActiveInSystem(userProfile,
						accountFromEmail);
			} else if (isAccountFromEmailWaitingInSystem(accountFromEmail)) {

				authenticationFacebookWithAccountWaitingInSystem(userProfile,
						accountFromEmail);
			}
		}
		return "redirect:/";
	}

	private boolean userFacebookNotAcceptSystemGetEmail(UserProfile userProfile) {
		return userProfile.getEmail() == null;
	}

	private void authenticationFacebookWithAccountWaitingInSystem(
			UserProfile userProfile, Account accountFromEmail) {
		String newPassword = utility.generatePassword();

		accountFromEmail.setDateModified(new Date());
		accountFromEmail.setUsername(userProfile.getEmail());
		accountFromEmail.setFirstName(userProfile.getFirstName());
		accountFromEmail.setLastName(userProfile.getLastName());
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
				userProfile.getEmail(), "", authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);

		notifyEmailAutoGeneratePassword(userProfile, newPassword);
	}

	private boolean isAccountFromEmailWaitingInSystem(Account accountFromEmail) {
		return accountFromEmail.getStatus().equals(
				Account.ACCOUNT_STATUS.WAITING);
	}

	private void authenticationFacebookWithAccountActiveInSystem(
			UserProfile userProfile, Account accountFromEmail) {
		accountFromEmail.setFirstName(userProfile.getFirstName());
		accountFromEmail.setLastName(userProfile.getLastName());
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

	private void authenticationFacebookWithAccountNotExistInSystem(
			UserProfile userProfile) {
		String newPassword = utility.generatePassword();
		// insert new account with username and email is the same
		Account createAccountFromFB = new Account();
		createAccountFromFB.setDateCreated(new Date());
		createAccountFromFB.setDateModified(new Date());
		createAccountFromFB.setUsername(userProfile.getEmail());
		createAccountFromFB.setFirstName(userProfile.getFirstName());
		createAccountFromFB.setLastName(userProfile.getLastName());
		createAccountFromFB.setEmail(userProfile.getEmail());
		createAccountFromFB.setStatus(ACCOUNT_STATUS.ACTIVE);
		createAccountFromFB.setPassword(utility
				.hashStringWithDefaultKey(newPassword));

		List<Role> userRoles = new ArrayList<>();
		userRoles.add(roleDao.getRoleByRoleName(Role.ROLE_NAME.USER));
		createAccountFromFB.setRoles(userRoles);
		accountDao.save(createAccountFromFB);

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new GrantedAuthorityImpl(String
				.valueOf(Role.ROLE_NAME.USER)));
		Authentication auth = new UsernamePasswordAuthenticationToken(
				userProfile.getEmail(), "", authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);

		notifyEmailAutoGeneratePassword(userProfile, newPassword);
	}

	private boolean accountNotExistInSystem(Account accountFromEmail) {
		return accountFromEmail == null;
	}

	private void notifyEmailAutoGeneratePassword(UserProfile userProfile,
			String newPassword) {
		// auto generate password and send email to user
		MailSenderDTO mailGeneratePassword = new MailSenderDTO();
		mailGeneratePassword.setContent(utility.getMessage(
				"msg.login.facebook.generate.password", userProfile.getName(),
				userProfile.getEmail(), newPassword));
		mailGeneratePassword.setSubject(utility
				.getMessage("msg.login.facebook.generate.password.subject"));
		mailGeneratePassword.setTo(userProfile.getEmail());
		mailService.sendHtmlMail(mailGeneratePassword);
	}

	private UserProfile getuserProfile(AccessGrant accessGrant) {
		Connection<Facebook> connection = facebookConnectionFactory
				.createConnection(accessGrant);
		return connection.fetchUserProfile();
	}

	private AccessGrant getAccessGrant(String authorizationCode) {
		OAuth2Operations oauthOperations = facebookConnectionFactory
				.getOAuthOperations();
		return oauthOperations.exchangeForAccess(authorizationCode,
				utility.getUrlSystem() + "/auth/facebook/callback", null);
	}

	private String getFacebookUserId(AccessGrant accessGrant) {
		Connection<Facebook> connection = facebookConnectionFactory
				.createConnection(accessGrant);
		ConnectionKey connectionKey = connection.getKey();
		return connectionKey.getProviderUserId();
	}

}
