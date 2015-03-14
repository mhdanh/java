package com.mhdanh.mytemplate.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mhdanh.mytemplate.service.CategoryService;
import com.mhdanh.mytemplate.service.UnzipService;
import com.mhdanh.mytemplate.service.TemplateService;
import com.mhdanh.mytemplate.utility.Utility;
import com.mhdanh.mytemplate.viewmodel.CommentTemplateModel;
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
	
	@RequestMapping(value = "/ajax/template/comment-template",method = RequestMethod.POST)
	@ResponseBody
	public Object ajaxCommentTemplate(@ModelAttribute("commentTemplate") CommentTemplateModel commentModel) throws IOException{
		return templateService.ajaxCommentTemplate(commentModel);
	}
	
	@RequestMapping(value = "/template/check/buy/direct/{idTemplate}")
	public String checkBuyTemplateDirect(Model model, @PathVariable("idTemplate") int idTemplate) throws IOException{
		return templateService.checkBuyTemplateDirect(model,idTemplate);
	}
	
	@RequestMapping(value = "/template/buy/direct/{idTemplate}")
	public void buyTemplateDirect(@PathVariable("idTemplate") int idTemplate,HttpServletResponse response) throws IOException{
		templateService.buyTemplateDirect(idTemplate,response);
	}
	
	@RequestMapping(value = "/template/edit-log/{idTemplate}")
	public String editLogTemplate(@PathVariable("idTemplate") int idTemplate,@RequestParam("log") String log){
		return templateService.editLogTemplate(idTemplate,log);
	}
	
	@RequestMapping(value = "/template/edit-log-page/{idTemplate}")
	public String pageEditLogTemplate(Model model,@PathVariable("idTemplate") int idTemplate){
		return templateService.pageEditLogTemplate(model,idTemplate);
	}
	
	@RequestMapping(value = "/template/edit-description/{idTemplate}")
	public String editDescriptionTemplate(@PathVariable("idTemplate") int idTemplate,@RequestParam("description") String description){
		return templateService.editDescriptionTemplate(idTemplate,description);
	}
	
	@RequestMapping(value = "/template/edit-description-page/{idTemplate}")
	public String pageEditDescriptionTemplate(Model model,@PathVariable("idTemplate") int idTemplate){
		return templateService.pageEditDescriptionTemplate(model,idTemplate);
	}
	
	@RequestMapping(value = "/template/delete-template")
	@ResponseBody
	public Object deleteTemplate(@RequestParam("idTemplate") int idTemplate){
		return templateService.deleteTemplate(idTemplate);
	}
	
	@RequestMapping(value = "/template/my-template")
	public String myTemplate(Model model) throws IOException{
		return templateService.myTemplate(model);
	}
	
	@RequestMapping(value = "/template/download-template-sample")
	public void downloadTemplateSample(HttpServletResponse response) throws IOException{
		utility.downloadFile(response, utility.getValueFromPropertiesSystemFile("system.url.store.template.sample"), "template-sample.zip");
	}
	
	@RequestMapping(value = "/template/download-template/{idTemplate}")
	public void downloadTemplateFree(@PathVariable("idTemplate") int idTemplate,HttpServletResponse response) throws IOException{
		templateService.downloadTemplateFree(idTemplate,response);
	}
	
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
	public Object uploadTemplateFile(@ModelAttribute("templateUpload") UploadTemplateDTO templateUpload,HttpServletRequest request) {
		return templateService.uploadTemplate(templateUpload);
	}
	
	@RequestMapping(value = "/ajax/check-template-upload-state",method = RequestMethod.POST)
	@ResponseBody
	public Object checkTemplateUploadState(@ModelAttribute("templateUpload") UploadTemplateDTO templateUpload){
		return templateService.checkFormatAndExistTemplate(templateUpload);
	}
}
