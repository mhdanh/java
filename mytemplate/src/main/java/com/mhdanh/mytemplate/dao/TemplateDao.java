package com.mhdanh.mytemplate.dao;

import com.mhdanh.mytemplate.domain.Template;

public interface TemplateDao extends CommonDao<Template>{
	Template getTemplateById(int idTemplate);
	Template getUploadTemplateByCategoryAndFileNameOfOwner(int categoryId,String fileName);
	Template getUploadTemplateByCategoryAndFileNameNotOwner(int categoryId,String fileName);
}
