package com.mhdanh.mytemplate.service;

import com.mhdanh.mytemplate.domain.UploadTemplate;
import com.mhdanh.mytemplate.viewmodel.UploadTemplateDTO;

public interface UploadTemplateService extends CommonService<UploadTemplate>{
	public boolean uploadTemplate(UploadTemplateDTO uploadTemplate);
}
