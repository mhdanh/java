package com.mhdanh.mytemplate.dao.implement;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mhdanh.mytemplate.dao.UploadTemplateDao;
import com.mhdanh.mytemplate.domain.UploadTemplate;
import com.mhdanh.mytemplate.utility.Utility;

@SuppressWarnings("unchecked")
@Transactional
@Repository
public class UploadTemplateDaoImpl extends CommonDaoImpl<UploadTemplate> implements UploadTemplateDao{

	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	Utility utility;
	
	@Override
	public UploadTemplate getUploadTemplateByCategoryAndFileNameOfOwner(
			int categoryId, String fileName) {
		UploadTemplate templateOfOwner = (UploadTemplate) sessionFactory.getCurrentSession()
				.createCriteria(UploadTemplate.class)
				.add(Restrictions.eq("category.id", categoryId))
				.add(Restrictions.eq("fileName",fileName))
				.add(Restrictions.eq("owner.id", utility.getUserLogined().getId()))
				.setMaxResults(1)
				.uniqueResult();
		return templateOfOwner;
	}

	@Override
	public UploadTemplate getUploadTemplateByCategoryAndFileNameNotOwner(
			int categoryId, String fileName) {
		UploadTemplate templateNotOwner = (UploadTemplate) sessionFactory.getCurrentSession()
				.createCriteria(UploadTemplate.class)
				.add(Restrictions.eq("category.id", categoryId))
				.add(Restrictions.eq("fileName",fileName))
				.add(Restrictions.not(Restrictions.eq("owner.id", utility.getUserLogined().getId())))
				.setMaxResults(1)
				.uniqueResult();
		return templateNotOwner;
	}


}
