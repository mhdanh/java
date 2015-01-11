package com.mhdanh.mytemplate.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mhdanh.mytemplate.domain.Template;
import com.mhdanh.mytemplate.service.TemplateService;

@Controller
public class IndexController {
	
	private Logger logger = Logger.getLogger(IndexController.class);
	
	@Autowired
	TemplateService uploadTemplateService;
	
	@RequestMapping(value={"/index","/","/index/"})
	public String index(Model model){
		List<Template> templates = uploadTemplateService.getAll();
		model.addAttribute("templates", templates);
		return "/index";
	}
}
