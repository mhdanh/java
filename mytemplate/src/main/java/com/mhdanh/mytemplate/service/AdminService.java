package com.mhdanh.mytemplate.service;

import org.springframework.ui.Model;

public interface AdminService {

	String initAllTemplateFromZipFile();

	String indexAdmin(Model model);

	String manageTemplate(Model model);
	/**
	 * 
	 * @param idTemplate
	 * @return json {status:true or file,msg:if success}
	 */
	Object publishTemplate(int idTemplate);
	/**
	 * 
	 * @param idTemplate
	 * @return json {status:true or file,msg:if success}
	 */
	Object unPublishTemplate(int idTemplate);

	String viewTemplate(int idTemplate);
	
}
