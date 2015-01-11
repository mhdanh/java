package com.mhdanh.mytemplate.dao.implement;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mhdanh.mytemplate.dao.TemplateDao;
import com.mhdanh.mytemplate.domain.Template;
import com.mhdanh.mytemplate.utility.Utility;

@SuppressWarnings("unchecked")
@Transactional
@Repository
public class TemplateDaoImpl extends CommonDaoImpl<Template> implements TemplateDao{

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private Utility utility;
	
	@Override
	public Template getUploadTemplateByCategoryAndFileNameOfOwner(
			int categoryId, String fileName) {
		Template templateOfOwner = (Template) sessionFactory.getCurrentSession()
				.createCriteria(Template.class)
				.add(Restrictions.eq("category.id", categoryId))
				.add(Restrictions.eq("fileName",fileName))
				.add(Restrictions.eq("owner.id", utility.getUserLogined().getId()))
				.setMaxResults(1)
				.uniqueResult();
		return templateOfOwner;
	}

	@Override
	public Template getUploadTemplateByCategoryAndFileNameNotOwner(
			int categoryId, String fileName) {
		Template templateNotOwner = (Template) sessionFactory.getCurrentSession()
				.createCriteria(Template.class)
				.add(Restrictions.eq("category.id", categoryId))
				.add(Restrictions.eq("fileName",fileName))
				.add(Restrictions.not(Restrictions.eq("owner.id", utility.getUserLogined().getId())))
				.setMaxResults(1)
				.uniqueResult();
		return templateNotOwner;
	}

	@Override
	public Template getTemplateById(int idTemplate) {
		return (Template) sessionFactory.getCurrentSession().createCriteria(Template.class)
				.add(Restrictions.eq("id", idTemplate))
				.uniqueResult();
	}


}
