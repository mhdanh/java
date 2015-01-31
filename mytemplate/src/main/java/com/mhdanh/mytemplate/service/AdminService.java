package com.mhdanh.mytemplate.service;

import org.springframework.ui.Model;

public interface AdminService {

	String initAllTemplateFromZipFile();

	String indexAdmin(Model model);
	
}
