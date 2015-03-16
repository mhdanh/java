package com.mhdanh.mytemplate.dao.implement;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mhdanh.mytemplate.dao.LikeOrDislikeDao;
import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.domain.Feedback;
import com.mhdanh.mytemplate.domain.LikeOrDislike;

@Transactional
@Repository
public class LikeOrDislikeDaoImpl extends CommonDaoImpl<LikeOrDislike> implements LikeOrDislikeDao{
 
	private final static Logger logger = Logger.getLogger(LikeOrDislikeDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public LikeOrDislike getById(int idLikeOrDislike) {
		try {
			return (LikeOrDislike) sessionFactory.getCurrentSession().get(LikeOrDislike.class, idLikeOrDislike);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error getById ",e);
			return null;
		}
	}
	@Override
	public LikeOrDislike getByLikerAndFeedback(Account liker, Feedback feedback) {
		try {
			return (LikeOrDislike) sessionFactory.getCurrentSession().createCriteria(LikeOrDislike.class)
					.add(Restrictions.eq("liker", liker))
					.add(Restrictions.eq("forFeedback", feedback))
					.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error getByLikerAndFeedback ",e);
			return null;
		}
	}
	
	
}
