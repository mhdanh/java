package com.mhdanh.mytemplate.service.implement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mhdanh.mytemplate.domain.Feedback;
import com.mhdanh.mytemplate.service.FeedbackService;

@Service
@Transactional
public class FeedbackServiceImpl extends CommonServiceImpl<Feedback> implements FeedbackService{
	
}
