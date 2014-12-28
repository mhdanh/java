package com.mhdanh.mytemplate.dao;

import com.mhdanh.mytemplate.domain.UploadTemplate;

public interface UploadTemplateDao extends CommonDao<UploadTemplate>{
	UploadTemplate getUploadTemplateByCategoryAndFileNameOfOwner(int categoryId,String fileName);
	UploadTemplate getUploadTemplateByCategoryAndFileNameNotOwner(int categoryId,String fileName);
}
