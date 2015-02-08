package com.mhdanh.mytemplate.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.mhdanh.mytemplate.domain.Template;
import com.mhdanh.mytemplate.viewmodel.LazyLoadTemplateFilterIndex;
import com.mhdanh.mytemplate.viewmodel.UploadTemplateDTO;

public interface TemplateService extends CommonService<Template>{
	
	List<Template> getAllTemplatePublished();
	int countTotalTemplatePublished();
	void downloadTemplateFree(int idTemplate, HttpServletResponse response);
	boolean checkTemplateFormat(MultipartFile fileTemplate);
	
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
	 * 	state:wrongformat,
	 * 	message:message wrong format
	 * }
	 * or
	 * {
	 * 	state:uploadsuccess,
	 * 	message:link to template detail: /template-detail/{idtemplate}
	 * }
	 * or
	 * {
	 * 	state:error,
	 * 	message:exception unexpected
	 * }
	 */
	Object uploadTemplate(UploadTemplateDTO uploadTemplate);
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
	Object checkFormatAndExistTemplate(UploadTemplateDTO templateUpload);
	List<Template> getLazyTemplatePublished(
			LazyLoadTemplateFilterIndex lazyLoadingCategory);
	int countTotalTemplatePublishedAndLazyLoadinTemplate(
			LazyLoadTemplateFilterIndex lazyLoadingTemplate);
	String myTemplate(Model model);
}
