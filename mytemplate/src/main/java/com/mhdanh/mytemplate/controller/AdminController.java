package com.mhdanh.mytemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mhdanh.mytemplate.service.AdminService;

@Controller
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@RequestMapping("/admin/init-all-template-from-zip-file")
	@ResponseBody
	public String initAllTemplateFromZipFile(){
		return adminService.initAllTemplateFromZipFile();
	}
	
	@RequestMapping("/admin/ajax/unpublish-template")
	@ResponseBody
	public Object unPublishTemplate(@RequestParam("idTemplate") int idTemplate){
		return adminService.unPublishTemplate(idTemplate);
	}
	
	@RequestMapping("/admin/ajax/publish-template")
	@ResponseBody
	public Object publishTemplate(@RequestParam("idTemplate") int idTemplate){
		return adminService.publishTemplate(idTemplate);
	}
	
	@RequestMapping(value = {"/admin/view-template/{idTemplate}"})
	public String viewTemplate(@PathVariable("idTemplate") int idTemplate){
		return adminService.viewTemplate(idTemplate);
	}
	
	@RequestMapping(value = {"/admin","admin/"})
	public String indexAdmin(Model model){
		return adminService.indexAdmin(model);
	}
	
	@RequestMapping(value = {"/admin/manage-template"})
	public String manageTemplate(Model model){
		return adminService.manageTemplate(model);
	}
}
