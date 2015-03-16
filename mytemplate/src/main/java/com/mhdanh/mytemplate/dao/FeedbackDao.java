package com.mhdanh.mytemplate.dao;

import java.util.List;

import com.mhdanh.mytemplate.domain.Feedback;

public interface FeedbackDao extends CommonDao<Feedback>{
	List<Feedback> getParentFeedbackOrderTimeDesc(int start, int limit);
	Feedback getFeedbackById(int idParentFeedback);
}
