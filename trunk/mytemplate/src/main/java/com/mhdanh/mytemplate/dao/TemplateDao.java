package com.mhdanh.mytemplate.dao;

import java.util.List;

import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.domain.Template;
import com.mhdanh.mytemplate.domain.Template.TEMPLATE_STATUS;
import com.mhdanh.mytemplate.viewmodel.LazyLoadTemplateFilterIndex;

public interface TemplateDao extends CommonDao<Template>{
	Template getTemplateById(int idTemplate);
	Template getUploadTemplateByCategoryAndFileNameOfOwner(int categoryId,String fileName);
	Template getUploadTemplateByCategoryAndFileNameNotOwner(int categoryId,String fileName);
	List<Template> getTemplateByStatus(Template.TEMPLATE_STATUS status);
	List<Template> getLazyTemplatePublished(
			LazyLoadTemplateFilterIndex lazyLoadingCategory);
	int countTemplateByStatus(TEMPLATE_STATUS statusTemplate);
	int countTotalTemplatePublishedAndLazyLoadinTemplate(
			LazyLoadTemplateFilterIndex lazyLoadingTemplate);
	List<Template> getNewestTemplate();
	List<Template> getTemplateNewestByOwner(Account userLogined);
}
