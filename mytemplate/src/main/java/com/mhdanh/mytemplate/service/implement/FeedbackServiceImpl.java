package com.mhdanh.mytemplate.service.implement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.mhdanh.mytemplate.dao.FeedbackDao;
import com.mhdanh.mytemplate.domain.Attachment;
import com.mhdanh.mytemplate.domain.Feedback;
import com.mhdanh.mytemplate.domain.Feedback.FEEDBACK_STATUS;
import com.mhdanh.mytemplate.domain.Feedback.FEEDBACK_TYPE;
import com.mhdanh.mytemplate.service.AttachmentService;
import com.mhdanh.mytemplate.service.FeedbackService;
import com.mhdanh.mytemplate.utility.Utility;
import com.mhdanh.mytemplate.viewmodel.FeedbackDTO;

@Service
@Transactional
public class FeedbackServiceImpl extends CommonServiceImpl<Feedback> implements FeedbackService{
	
	private final static Logger logger = Logger.getLogger(FeedbackServiceImpl.class);
	
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private FeedbackDao feedbackDao;
	@Autowired
	private Utility utility;
	
	@Override
	public String indexPage(Model model) {
		int start = 0;
		int limit = 10;
		List<Feedback> feedbacks = feedbackDao.getParentFeedbackOrderTimeDesc(start,limit);
		model.addAttribute("feedBackTypes", Feedback.FEEDBACK_TYPE.values());
		model.addAttribute("feedbacks", feedbacks);
		return "/feedback";
	}

	@Override
	public String feedBackAdd(FeedbackDTO feedBackData) {
		try {
			Feedback addFeedback = new Feedback();
			if(feedBackData.getFileFeedback() != null) {
				//save attachment
				Attachment attachment =  attachmentService.saveAttachmentFeedback(feedBackData.getFileFeedback());
				if(attachment != null) {
					addFeedback.setAttachment(attachment);
				}
			}
			Feedback parentFeedBack = feedbackDao.getFeedbackById(feedBackData.getIdParentFeedback());
			addFeedback.setParentFeedback(parentFeedBack);
			addFeedback.setSubject(feedBackData.getSubjectFeedback());
			addFeedback.setContent(feedBackData.getContentFeedback());
			if(feedBackData.getTypeFeedback() != null && !feedBackData.getTypeFeedback().trim().isEmpty()){
				addFeedback.setType(FEEDBACK_TYPE.valueOf(feedBackData.getTypeFeedback()));
			}
			addFeedback.setDateCreated(new Date());
			addFeedback.setStatus(FEEDBACK_STATUS.OPEN);
			addFeedback.setFeedbacker(utility.getUserLogined());
			feedbackDao.save(addFeedback);
			return "redirect:/feedback";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error feedBackAdd ",e);
			return "/error-page";
		}
	}
}
