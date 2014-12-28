package com.mhdanh.mytemplate.service;

import com.mhdanh.mytemplate.domain.UploadTemplate;
import com.mhdanh.mytemplate.viewmodel.UploadTemplateDTO;

public interface UploadTemplateService extends CommonService<UploadTemplate>{
	boolean uploadTemplate(UploadTemplateDTO uploadTemplate);
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
}
