package com.mhdanh.mytemplate.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.http.auth.Credentials;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.googlecode.googleplus.GooglePlusFactory;
import com.googlecode.googleplus.Plus;
import com.googlecode.googleplus.core.GooglePlusConnectionFactory;
import com.googlecode.googleplus.core.OAuth2RefreshListener;
import com.googlecode.googleplus.model.person.Person;
import com.googlecode.googleplus.model.person.PersonEmails;

@Controller
public class GooglePlusController {

	@RequestMapping("/auth/google")
	public String authGoogle(HttpSession session) {
		GooglePlusFactory factory = new GooglePlusFactory("12512827598-146jh2p17c811adk1u27p1fobme18nrs.apps.googleusercontent.com", "RwAuH7ue_aeOYYsLdBodS03i");
		OAuth2Parameters oAuthParams = new OAuth2Parameters();
		oAuthParams.setRedirectUri("http://localhost:8080/auth/google/callback");
		oAuthParams.setScope("https://www.googleapis.com/auth/userinfo.email");
		String url = factory.getOAuthOperations().buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, oAuthParams);
		return "redirect:" + url;
	}

	@RequestMapping("/auth/google/callback")
	public String authGoogleCallBack(@RequestParam("code") String code) {

		GooglePlusFactory factory = new GooglePlusFactory("12512827598-146jh2p17c811adk1u27p1fobme18nrs.apps.googleusercontent.com", "RwAuH7ue_aeOYYsLdBodS03i");
		AccessGrant accessGrant = getAccessGrant(code);
		Plus plus = factory.getApi(accessGrant.getAccessToken());
		Person p = plus.getPeopleOperations().get("me");
		List<PersonEmails> emails = p.getEmails();
		for(PersonEmails pe :emails){
			System.out.println(pe.getValue());
		}
		return "redirect:/";
	}

	private AccessGrant getAccessGrant(String authorizationCode) {
		GooglePlusFactory factory = new GooglePlusFactory("12512827598-146jh2p17c811adk1u27p1fobme18nrs.apps.googleusercontent.com", "RwAuH7ue_aeOYYsLdBodS03i");
		OAuth2Operations oauthOperations = factory
				.getOAuthOperations();
		return oauthOperations.exchangeForAccess(authorizationCode,
				"http://localhost:8080/auth/google/callback", null);
	}
	
	
	
}
