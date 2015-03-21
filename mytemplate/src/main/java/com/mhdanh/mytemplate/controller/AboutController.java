package com.mhdanh.mytemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mhdanh.mytemplate.utility.Utility;

@Controller
public class AboutController {
	
	@Autowired
	private Utility utility;
	
	@RequestMapping("/about")
	public String aboutPage(Model model) {
		String linkContact =utility.getUrlSystem() + "/contact";
		model.addAttribute("descriptionForRequestTemplateOrWebsite", utility.getMessage("msg.aboutme.description.request.template.or.website", linkContact));
		return "/about";
	}
	
}
