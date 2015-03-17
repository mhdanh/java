package com.mhdanh.mytemplate.dao.implement;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mhdanh.mytemplate.dao.ContactUsDao;
import com.mhdanh.mytemplate.domain.ContactUs;

@Transactional
@Repository
public class ContactUsDaoImpl extends CommonDaoImpl<ContactUs> implements ContactUsDao{
 
	private final static Logger logger = Logger.getLogger(ContactUsDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
}
