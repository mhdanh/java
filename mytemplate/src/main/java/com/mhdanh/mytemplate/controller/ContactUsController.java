package com.mhdanh.mytemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mhdanh.mytemplate.service.ContactUsService;
import com.mhdanh.mytemplate.viewmodel.ContactUsDTO;

@Controller
public class ContactUsController {

	
	@Autowired
	private ContactUsService contactUsService;
	
	@RequestMapping(value = {"/contact","/contact/"})
	public String contact(Model model){
		return "/contact";
	}
	
	@RequestMapping(value = {"/contact/add"})
	public String contactAdd(@ModelAttribute("contactForm") ContactUsDTO contactData){
		return contactUsService.contactAdd(contactData);
	}
	
}
