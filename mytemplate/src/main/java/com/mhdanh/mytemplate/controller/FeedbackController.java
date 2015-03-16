package com.mhdanh.mytemplate.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@RequestMapping(value = {"/feedback/download-attachment/{idAttachment}"})
	public String downloadAttachmentFeedback(@PathVariable("idAttachment") int idAttachment, HttpServletResponse response) {
		return feedbackService.downloadAttachmentFeedback(idAttachment,response);
	}
	
	@RequestMapping(value = {"/feedback/ajax/like"})
	@ResponseBody
	public Object likeFeedback(@RequestParam("idFeedback") int idFeedback) {
		return feedbackService.likeFeedback(idFeedback);
	}
	
	@RequestMapping(value = {"/feedback/ajax/dislike"})
	@ResponseBody
	public Object dislLikeFeedback(@RequestParam("idFeedback") int idFeedback) {
		return feedbackService.disLikeFeedback(idFeedback);
	}
	
}
