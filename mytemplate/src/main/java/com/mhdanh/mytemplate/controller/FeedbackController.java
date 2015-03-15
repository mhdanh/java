package com.mhdanh.mytemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mhdanh.mytemplate.service.FeedbackService;
import com.mhdanh.mytemplate.viewmodel.FeedbackDTO;

@Controller
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;
	
	@RequestMapping(value = {"/feedback","/feedback/"})
	public String indexPage(Model model) {
		return feedbackService.indexPage(model);
	}
	
	@RequestMapping(value = {"/feedback/add"})
	public String feedBackAdd(@ModelAttribute("feedBackForm") FeedbackDTO feedBackData) {
		return feedbackService.feedBackAdd(feedBackData);
	}
	
}
