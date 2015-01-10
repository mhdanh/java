package com.mhdanh.mytemplate.dao.implement;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.mhdanh.mytemplate.dao.FeedbackDao;
import com.mhdanh.mytemplate.domain.Feedback;

@Transactional
@Repository
public class FeedbackDaoImpl extends CommonDaoImpl<Feedback> implements FeedbackDao{


}
