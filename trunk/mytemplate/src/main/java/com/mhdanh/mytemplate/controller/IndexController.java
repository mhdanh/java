package com.mhdanh.mytemplate.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mhdanh.mytemplate.domain.UploadTemplate;
import com.mhdanh.mytemplate.service.UploadTemplateService;

@Controller
public class IndexController {
	
	private Logger logger = Logger.getLogger(IndexController.class);
	
	@Autowired
	UploadTemplateService uploadTemplateService;
	
	@RequestMapping(value={"/index","/","/index/"})
	public String index(Model model){
		List<UploadTemplate> templates = uploadTemplateService.getAll();
		model.addAttribute("templates", templates);
		return "/index";
	}
}
