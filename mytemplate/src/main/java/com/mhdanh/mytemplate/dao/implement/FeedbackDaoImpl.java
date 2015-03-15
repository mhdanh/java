package com.mhdanh.mytemplate.dao.implement;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mhdanh.mytemplate.dao.FeedbackDao;
import com.mhdanh.mytemplate.domain.Feedback;

@Transactional
@Repository
public class FeedbackDaoImpl extends CommonDaoImpl<Feedback> implements FeedbackDao{
 
	private final static Logger logger = Logger.getLogger(FeedbackDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Feedback> getParentFeedbackOrderTimeDesc(int start, int limit) {
		try {
			return sessionFactory.getCurrentSession()
					.createCriteria(Feedback.class)
					.add(Restrictions.isNull("parentFeedback"))
					.addOrder(Order.desc("dateCreated"))
					.setFirstResult(start)
					.setMaxResults((start + 1)*limit)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error getFeedbackOrderTimeDesc ",e);
			return new ArrayList<>();
		}
	}

	@Override
	public Feedback getFeedbackById(int idParentFeedback) {
		return (Feedback) sessionFactory.getCurrentSession().get(Feedback.class, idParentFeedback);
	}
}
