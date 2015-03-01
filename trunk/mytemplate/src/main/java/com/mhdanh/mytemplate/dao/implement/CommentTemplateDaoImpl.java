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

import com.mhdanh.mytemplate.dao.CommentTemplateDao;
import com.mhdanh.mytemplate.domain.CommentTemplate;
import com.mhdanh.mytemplate.domain.Template;

@SuppressWarnings("unchecked")
@Transactional
@Repository
public class CommentTemplateDaoImpl extends CommonDaoImpl<CommentTemplate> implements CommentTemplateDao{

	private static final Logger logger = Logger.getLogger(CommentTemplateDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<CommentTemplate> getCommentsParentByTemplate(
			Template templateById) {
		try {
			return sessionFactory.getCurrentSession()
					.createCriteria(CommentTemplate.class)
					.add(Restrictions.eq("template", templateById))
					.add(Restrictions.isNull("parentComment"))
					.addOrder(Order.desc("dateCreated"))
					.list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get comments parent by template unsuccessful",e);
		}
		return new ArrayList<>();
	}
}
