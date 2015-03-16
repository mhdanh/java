package com.mhdanh.mytemplate.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.mhdanh.mytemplate.domain.Feedback;
import com.mhdanh.mytemplate.viewmodel.FeedbackDTO;

public interface FeedbackService extends CommonService<Feedback> {

	String indexPage(Model model);

	String feedBackAdd(FeedbackDTO feedBackData);

	String downloadAttachmentFeedback(int idAttachment,
			HttpServletResponse response);

	/**
	 * 
	 * @param idFeedback
	 * @return ajax status..
	 */
	Object likeFeedback(int idFeedback);

	/**
	 * 
	 * @param idFeedback
	 * @return ajax status..
	 */
	Object disLikeFeedback(int idFeedback);
}
