package com.mhdanh.mytemplate.utility;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.service.AccountService;

@Component
public class Utility {

	@Autowired
	MessageSource messageSource;
	@Autowired
	AccountService accountService;
	
	public String getWebappPath() throws UnsupportedEncodingException {
		String classPath = this.getClass().getClassLoader().getResource("")
				.getPath();
		String fullPath = URLDecoder.decode(classPath, "UTF-8");
		String pathArr[] = fullPath.split("/WEB-INF/classes/");
		String webappPath = pathArr[0];
		return webappPath;
	}
	/**
	 * 
	 * @return /view/html/
	 * 
	 */
	public String getHtmlWebappPath() throws UnsupportedEncodingException {
		return this.getWebappPath() + "/view/layout/";
	}

	public String getMessage(String key) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(key, null, locale);
	}
	
	/**
	 * Example: CATEGORY.ADMIN_DASHBOARD to admin-dashboard
	 * @param dataBaseText
	 * @return
	 */
	public String convertTextInDatabaseToNormalText(String dataBaseText){
		String [] splipedTextByDot = dataBaseText.split("\\.");
		if(splipedTextByDot.length == 0){
			return null;
		}
		return splipedTextByDot[1].toLowerCase().replace('_', '-');
	}
	
	/**
	 * Get account of current logined
	 * 
	 * 
	 */
	public Account getUserLogined(){
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	     String username = auth.getName();
	     return accountService.getAccountByUsername(username);
	}
	/**
	 * Exmple: first-template.zip to first-template 
	 * 
	 */
	public String getNameWithouExtension(String fileName){
		String [] splitedTextByDot = fileName.split("\\.");
		if(splitedTextByDot.length == 0){
			return null;
		}
		return splitedTextByDot[0];
	}

}
