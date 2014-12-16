package com.mhdanh.mytemplate.controller;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	private static final Logger LOGGER = Logger.getLogger(IndexController.class);
	
	@RequestMapping(value={"/index","/","/index/"})
	public String index(Model model){
		System.out.println("ok man");
		LOGGER.debug("-----debug LOGGER-----");
		LOGGER.warn("-----warn LOGGER-----");
		LOGGER.info("-----info LOGGER-----");
		LOGGER.error("-----error LOGGER-----");
		LOGGER.fatal("-----fatal LOGGER-----");
		model.addAttribute("ok", "hi every one");
		return "/index";
	}
}
