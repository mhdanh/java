package com.mhdanh.mytemplate.utility;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.stereotype.Component;

@Component
public class Utility {
	
	public String getWebappPath() throws UnsupportedEncodingException{
		String classPath = this.getClass().getClassLoader().getResource("").getPath();
		String fullPath = URLDecoder.decode(classPath, "UTF-8");
		String pathArr[] = fullPath.split("/WEB-INF/classes/");
		String webappPath = pathArr[0];
		return webappPath;
	}
	
	public String getHtmlWebappPath() throws UnsupportedEncodingException{
		return this.getWebappPath() + "/view/html/";
	}

}
