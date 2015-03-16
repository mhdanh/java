package com.mhdanh.mytemplate.service.implement;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.mhdanh.mytemplate.dao.FeedbackDao;
import com.mhdanh.mytemplate.dao.LikeOrDislikeDao;
import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.domain.Attachment;
import com.mhdanh.mytemplate.domain.Feedback;
import com.mhdanh.mytemplate.domain.Feedback.FEEDBACK_STATUS;
import com.mhdanh.mytemplate.domain.Feedback.FEEDBACK_TYPE;
import com.mhdanh.mytemplate.domain.LikeOrDislike;
import com.mhdanh.mytemplate.domain.LikeOrDislike.LIKE_DISLIKE_TYPE;
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
	@Autowired
	private LikeOrDislikeDao likeOrDislikeDao;
	
	@Override
	public String indexPage(Model model) {
		int start = 0;
		int limit = 10;
		List<Feedback> feedbacks = feedbackDao.getParentFeedbackOrderTimeDesc(start,limit);
		model.addAttribute("feedBackTypes", Feedback.FEEDBACK_TYPE.values());
		model.addAttribute("feedbacks", feedbacks);
		model.addAttribute("userLogined", utility.getUserLogined());
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
	
	@Override
	public String downloadAttachmentFeedback(int idAttachment,
			HttpServletResponse response) {
		try {
			Attachment attachmentById = attachmentService.getById(idAttachment);
			if(attachmentById != null) {
				if(!utility.downloadFile(response, attachmentById.getFullLink(), attachmentById.getFileName())){
					return "redirect:/feedback";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error downloadAttachmentFeedback ",e);
		}
		return "redirect:/feedback";
	}
	
	@Override
	public Object likeFeedback(int idFeedback) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			Account liker = utility.getUserLogined();
			Feedback feedBackById = feedbackDao.getFeedbackById(idFeedback);
			if(feedBackById != null){
				LikeOrDislike likeOrDislikeByLikerAndFeedback = likeOrDislikeDao.getByLikerAndFeedback(liker, feedBackById);
				if(likeOrDislikeByLikerAndFeedback != null){
					if(likeOrDislikeByLikerAndFeedback.getType().compareTo(LIKE_DISLIKE_TYPE.LIKE) == 0) {
						//delete
						likeOrDislikeDao.delete(likeOrDislikeByLikerAndFeedback.getId());
					} else {
						//update
						likeOrDislikeByLikerAndFeedback.setType(LIKE_DISLIKE_TYPE.LIKE);
						likeOrDislikeByLikerAndFeedback.setDateCreated(new Date());
						likeOrDislikeDao.update(likeOrDislikeByLikerAndFeedback);
					}
				} else {
					//add new
					LikeOrDislike like = new LikeOrDislike();
					like.setDateCreated(new Date());
					like.setForFeedback(feedBackById);
					like.setLiker(liker);
					like.setType(LIKE_DISLIKE_TYPE.LIKE);
					likeOrDislikeDao.save(like);
				}
				resultMap.put("state", "true");
			} else {
				resultMap.put("state", "false");
				resultMap.put("msg", "Feedback not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error likeFeedback ",e);
			resultMap.put("state", "false");
			resultMap.put("msg", "error");
		}
		return resultMap;
	}

	@Override
	public Object disLikeFeedback(int idFeedback) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			Account liker = utility.getUserLogined();
			Feedback feedBackById = feedbackDao.getFeedbackById(idFeedback);
			if(feedBackById != null){
				LikeOrDislike likeOrDislikeByLikerAndFeedback = likeOrDislikeDao.getByLikerAndFeedback(liker, feedBackById);
				if(likeOrDislikeByLikerAndFeedback != null){
					if(likeOrDislikeByLikerAndFeedback.getType().compareTo(LIKE_DISLIKE_TYPE.DISLIKE) == 0) {
						//delete
						likeOrDislikeDao.delete(likeOrDislikeByLikerAndFeedback.getId());
					} else {
						//update
						likeOrDislikeByLikerAndFeedback.setType(LIKE_DISLIKE_TYPE.DISLIKE);
						likeOrDislikeByLikerAndFeedback.setDateCreated(new Date());
						likeOrDislikeDao.update(likeOrDislikeByLikerAndFeedback);
					}
					
				} else {
					//add new
					LikeOrDislike like = new LikeOrDislike();
					like.setDateCreated(new Date());
					like.setForFeedback(feedBackById);
					like.setLiker(liker);
					like.setType(LIKE_DISLIKE_TYPE.DISLIKE);
					likeOrDislikeDao.save(like);
				}
				resultMap.put("state", "true");
			} else {
				resultMap.put("state", "false");
				resultMap.put("msg", "Feedback not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error likeFeedback ",e);
			resultMap.put("state", "false");
			resultMap.put("msg", "error");
		}
		return resultMap;
	}
}
