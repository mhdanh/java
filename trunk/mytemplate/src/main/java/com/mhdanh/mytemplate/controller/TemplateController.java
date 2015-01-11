package com.mhdanh.mytemplate.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mhdanh.mytemplate.service.CategoryService;
import com.mhdanh.mytemplate.service.UnzipService;
import com.mhdanh.mytemplate.service.TemplateService;
import com.mhdanh.mytemplate.utility.Utility;
import com.mhdanh.mytemplate.viewmodel.UploadTemplateDTO;

@Controller
public class TemplateController {
	
private Logger logger = Logger.getLogger(TemplateController.class);
	
	@Autowired
	private Utility utility;
	@Autowired
	private UnzipService unzipService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private TemplateService templateService;
	
	
	@RequestMapping(value = "/template-detail/{idtemplate}")
	public String templateDetailPage(Model model,@PathVariable("idtemplate") int idTemplate){
		return templateService.templateDetail(model,idTemplate);
	}
	
	@RequestMapping(value = "/upload-template-file-page")
	public String uploadTemplateFilePage(Model model){
		model.addAttribute("categories", categoryService.getAll());
		return "/upload-template-file-page";
	}
	
	@RequestMapping(value = "/ajax/upload-template-file-page", method = RequestMethod.POST)
	@ResponseBody
	public String uploadTemplateFile(@ModelAttribute("templateUpload") UploadTemplateDTO templateUpload,HttpServletRequest request) {
		int idNewTemplate = templateService.uploadTemplate(templateUpload);
		String linkToTemplateDetail = utility.getUrlSystem() + "/template-detail/" + idNewTemplate;
		return linkToTemplateDetail;
	}
	
	@RequestMapping(value = "/ajax/check-template-upload-state",method = RequestMethod.POST)
	@ResponseBody
	public Object checkTemplateUploadState(@ModelAttribute("templateUpload") UploadTemplateDTO templateUpload){
		return templateService.checkFormatAndExistTemplate(templateUpload);
		
	}
}
