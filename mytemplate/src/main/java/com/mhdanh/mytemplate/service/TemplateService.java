package com.mhdanh.mytemplate.service;

import org.springframework.ui.Model;

import com.mhdanh.mytemplate.domain.Template;
import com.mhdanh.mytemplate.viewmodel.UploadTemplateDTO;

public interface TemplateService extends CommonService<Template>{
	/**
	 * 
	 * @param uploadTemplate
	 * @return id newtemplate
	 */
	int uploadTemplate(UploadTemplateDTO uploadTemplate);
	/**
	 * return json object. 
	 * {
	 * 	state:isusedbyothermember,
	 * 	message:file name is used by other member of this category	
	 * }
	 * or
	 * {
	 * 	state:overwriteyourtemplate,
	 * 	message:you are overwriting your template on this category	
	 * }
	 * or
	 * {
	 * 	state:canuse
	 * }
	 */
	Object checkkUploadTemplateState(int categoryId,String fileName);
	String templateDetail(Model model, int idTemplate);
}
