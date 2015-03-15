package com.mhdanh.mytemplate.service;

import org.springframework.ui.Model;

import com.mhdanh.mytemplate.domain.Feedback;
import com.mhdanh.mytemplate.viewmodel.FeedbackDTO;

public interface FeedbackService extends CommonService<Feedback>{

	String indexPage(Model model);

	String feedBackAdd(FeedbackDTO feedBackData);
}
