package com.mhdanh.mytemplate.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.service.AccountService;

@Component
public class Utility {

	private final Logger logger = Logger.getLogger(Utility.class);
	
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @return http://localhost:8080/mytemplate
	 */
	public String getUrlSystem(){
		HttpServletRequest request = (((ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes()).getRequest());
		String systemUrl = request.getScheme()+ "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		return systemUrl;
	}
	
	public String getValidatedValue(String valueNeedToValidate){
		return valueNeedToValidate.trim().replaceAll("\\p{Cntrl}", "");
	}
	
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
	
	public String getMessage(String key,Object... param) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(key, param, locale);
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
	
	public String getPropValues(String filename,String key){
		Properties prop = new Properties();
		String propFileName = filename;
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			logger.error("cannot read properties file", e);
		}
		// get the property value and print it out
		String valueKey = prop.getProperty(key);
		return valueKey;
	}
	
	public String getValueFromPropertiesSystemFile(String key){
		Properties prop = new Properties();
		String propFileName = "system.properties";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			logger.error("cannot read properties file", e);
		}
		return prop.getProperty(key);
	}
	
	/**
	 * return string after hashing by md5 
	 */
	public String hashStringWithDefaultKey(String string){
		try {
			string = string + "myui.info";
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(string.getBytes());
	        byte byteData[] = md.digest();
	        //convert the byte to hex format method 1
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        return sb.toString();
		} catch (Exception e) {
			logger.error("Error hashing string",e);
			return null;
		}
	}
	
	/**
	 * return string after hashing by md5 
	 */
	public String hashString(String string){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(string.getBytes());
	        byte byteData[] = md.digest();
	        //convert the byte to hex format method 1
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        return sb.toString();
		} catch (Exception e) {
			logger.error("Error hashing string",e);
			return null;
		}
	}
	/**
	 * hash file into string 
	 */
	public String hashFile(File fileBeHashed){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
	        FileInputStream fis = new FileInputStream(fileBeHashed);
	        byte[] dataBytes = new byte[1024];
	        int nread = 0; 
	        while ((nread = fis.read(dataBytes)) != -1) {
	          md.update(dataBytes, 0, nread);
	        };
	        fis.close();
	        byte[] mdbytes = md.digest();
	        //convert the byte to hex format method 1
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < mdbytes.length; i++) {
	          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        return sb.toString();
		} catch (Exception e) {
			logger.error("Error hashing file",e);
			return null;
		}
	}
	/**
	 *  render img for view from phycial storage
	 */
	public void viewImg(HttpServletResponse response,String outputPath,String filename) throws IOException{
		File file = new File(outputPath);
		if(file.exists()){
			FileInputStream inputStream= new FileInputStream(file);
			ServletOutputStream outStream = response.getOutputStream();
	        byte[] buffer = new byte[4096];
	        int bytesRead = -1;
	        response.setContentType("image/*");
			response.setHeader("Content-Disposition","inline;filename="+filename);
			response.setContentLength((int) file.length());
	        while ((bytesRead = inputStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	        }
	        inputStream.close();
	        outStream.close();
		}
	}
	
}
