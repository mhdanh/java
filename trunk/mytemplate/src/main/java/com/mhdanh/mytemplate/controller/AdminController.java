package com.mhdanh.mytemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mhdanh.mytemplate.service.AdminService;

@Controller
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@RequestMapping("/admin/init-all-template-from-zip-file")
	@ResponseBody
	private String initAllTemplateFromZipFile(){
		return adminService.initAllTemplateFromZipFile();
	}
	
	@RequestMapping(value = {"/admin","admin/"})
	private String indexAdmin(Model model){
		return adminService.indexAdmin(model);
	}
}
