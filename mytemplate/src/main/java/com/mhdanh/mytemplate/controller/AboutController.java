package com.mhdanh.mytemplate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AboutController {

	@RequestMapping(value = {"/contact","/contact/"})
	public String contact(Model model){
		return "/contact";
	}
	
	@RequestMapping(value = {"/contact/add"})
	public String contactAdd(){
		return "/contact";
	}
	
}
