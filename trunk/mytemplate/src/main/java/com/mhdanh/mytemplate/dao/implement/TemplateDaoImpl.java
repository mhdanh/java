package com.mhdanh.mytemplate.dao.implement;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mhdanh.mytemplate.dao.TemplateDao;
import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.domain.Template;
import com.mhdanh.mytemplate.domain.Template.TEMPLATE_STATUS;
import com.mhdanh.mytemplate.utility.Utility;
import com.mhdanh.mytemplate.viewmodel.HardCode;
import com.mhdanh.mytemplate.viewmodel.LazyLoadTemplateFilterIndex;

@SuppressWarnings("unchecked")
@Transactional
@Repository
public class TemplateDaoImpl extends CommonDaoImpl<Template> implements TemplateDao{

	private static final Logger logger = Logger.getLogger(TemplateDaoImpl.class);
	
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

	@Override
	public List<Template> getTemplateByStatus(TEMPLATE_STATUS status) {
		return sessionFactory.getCurrentSession().createCriteria(Template.class)
				.add(Restrictions.eq("status", status))
				.addOrder(Order.desc("dateModified"))
				.list();
	}

	@Override
	public List<Template> getLazyTemplatePublished(
			LazyLoadTemplateFilterIndex lazyLoadingTemplate) {
		Criteria lazyLoadTemplateCriteria = sessionFactory.getCurrentSession()
				.createCriteria(Template.class)
				.add(Restrictions.eq("status", TEMPLATE_STATUS.PUBLISHED));
		Integer zero = 0;
		if(lazyLoadingTemplate.getIdCategory() != zero) {
			lazyLoadTemplateCriteria.add(Restrictions.eq("category.id", lazyLoadingTemplate.getIdCategory()));
		}
		
		if(HardCode.topDownload.equals(lazyLoadingTemplate.getValueFilter())){
			lazyLoadTemplateCriteria.addOrder(Order.desc("buy"));
		}
		
		if(HardCode.topFreeDownload.equals(lazyLoadingTemplate.getValueFilter())){
			lazyLoadTemplateCriteria.add(Restrictions.eq("sellOff", zero));
			lazyLoadTemplateCriteria.addOrder(Order.desc("buy"));
		}
		
		if(HardCode.topPremiumDownload.equals(lazyLoadingTemplate.getValueFilter())){
			lazyLoadTemplateCriteria.add(Restrictions.not(Restrictions.eq("sellOff", zero)));
			lazyLoadTemplateCriteria.addOrder(Order.desc("buy"));
		}
		
		if(HardCode.newest.equals(lazyLoadingTemplate.getValueFilter())){
			lazyLoadTemplateCriteria.addOrder(Order.desc("dateModified"));
		}
		
		if(HardCode.freeNewest.equals(lazyLoadingTemplate.getValueFilter())){
			lazyLoadTemplateCriteria.add(Restrictions.eq("sellOff", zero));
			lazyLoadTemplateCriteria.addOrder(Order.desc("dateModified"));
		}
		
		if(HardCode.premiumNewest.equals(lazyLoadingTemplate.getValueFilter())){
			lazyLoadTemplateCriteria.add(Restrictions.not(Restrictions.eq("sellOff", zero)));
			lazyLoadTemplateCriteria.addOrder(Order.desc("dateModified"));
		}
		
		lazyLoadTemplateCriteria.setFirstResult(lazyLoadingTemplate.getPage() * lazyLoadingTemplate.getStep());
		lazyLoadTemplateCriteria.setMaxResults(lazyLoadingTemplate.getStep());
		return lazyLoadTemplateCriteria.list();
	}

	@Override
	public int countTemplateByStatus(TEMPLATE_STATUS statusTemplate) {
		logger.warn("begin count template by status");
		try {
			String hqlCountTemplateByStatus = "SELECT count(*) FROM Template template"
					+ " WHERE template.status = :templateStatus";
			Long totalRowTemplateByStatus =  (Long)sessionFactory.getCurrentSession()
					.createQuery(hqlCountTemplateByStatus)
					.setParameter("templateStatus", statusTemplate)
					.uniqueResult();
			return totalRowTemplateByStatus.intValue();
		} catch (Exception e) {
			System.out.println("error count template by status unsuccessful: " + e);
			logger.error("error count template by status unsuccessful: ",e);
			return 0;
		}
	}

	@Override
	public int countTotalTemplatePublishedAndLazyLoadinTemplate(
			LazyLoadTemplateFilterIndex lazyLoadingTemplate) {
		logger.warn("begin count total template published by lazy loading template");
		try {
			Criteria lazyLoadTemplateCriteria = sessionFactory.getCurrentSession()
					.createCriteria(Template.class)
					.add(Restrictions.eq("status", TEMPLATE_STATUS.PUBLISHED));
			Integer zero = 0;
			if(lazyLoadingTemplate.getIdCategory() != zero) {
				lazyLoadTemplateCriteria.add(Restrictions.eq("category.id", lazyLoadingTemplate.getIdCategory()));
			}
			
			if(HardCode.topFreeDownload.equals(lazyLoadingTemplate.getValueFilter())){
				lazyLoadTemplateCriteria.add(Restrictions.eq("sellOff", zero));
			}
			
			if(HardCode.topPremiumDownload.equals(lazyLoadingTemplate.getValueFilter())){
				lazyLoadTemplateCriteria.add(Restrictions.not(Restrictions.eq("sellOff", zero)));
			}
			
			if(HardCode.freeNewest.equals(lazyLoadingTemplate.getValueFilter())){
				lazyLoadTemplateCriteria.add(Restrictions.eq("sellOff", zero));
			}
			
			if(HardCode.premiumNewest.equals(lazyLoadingTemplate.getValueFilter())){
				lazyLoadTemplateCriteria.add(Restrictions.not(Restrictions.eq("sellOff", zero)));
			}
			lazyLoadTemplateCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			Long totalRow = (Long) lazyLoadTemplateCriteria.setProjection(Projections.rowCount()).uniqueResult();
			return totalRow.intValue();
		} catch (Exception e) {
			System.out.println("error count total template published by lazy loading template: " + e);
			logger.error("error count total template published by lazy loading template:",e);
			return 0;
		}
		
	}

	@Override
	public List<Template> getNewestTemplate() {
		logger.warn("begin template dao get newest templates");
		try {
			return sessionFactory.getCurrentSession()
					.createCriteria(Template.class)
					.addOrder(Order.desc("dateModified"))
					.list();
		} catch (Exception e) {
			System.out.println("error get newest template:" + e);
			logger.error("error get newest template:",e);
			return new ArrayList<>();
		}
	}

	@Override
	public List<Template> getTemplateNewestByOwner(
			Account userLogined) {
		logger.warn("begin daoimpl get template newest by owner");
		try {
			return sessionFactory.getCurrentSession()
					.createCriteria(Template.class)
					.add(Restrictions.eqOrIsNull("owner.id", userLogined.getId()))
					.addOrder(Order.desc("dateModified"))
					.list();
		} catch (Exception e) {
			System.out.println("error get template newest by owner unsuccessful" + e);
			logger.error("error get template newest by owner unsuccessful",e);
			return new ArrayList<>();
		}
	}
}
