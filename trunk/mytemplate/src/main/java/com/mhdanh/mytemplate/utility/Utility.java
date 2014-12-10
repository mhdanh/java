package com.mhdanh.mytemplate.utility;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Utility {

	@Autowired
	MessageSource messageSource;
	
	public String getWebappPath() throws UnsupportedEncodingException {
		String classPath = this.getClass().getClassLoader().getResource("")
				.getPath();
		String fullPath = URLDecoder.decode(classPath, "UTF-8");
		String pathArr[] = fullPath.split("/WEB-INF/classes/");
		String webappPath = pathArr[0];
		return webappPath;
	}

	public String getHtmlWebappPath() throws UnsupportedEncodingException {
		return this.getWebappPath() + "/view/html/";
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
		String [] slipedTextByDot = dataBaseText.split(".");
		if(slipedTextByDot.length == 0){
			return null;
		}
		return slipedTextByDot[1].toLowerCase();
	}

}
