package com.mhdanh.mytemplate.dao.implement;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.mhdanh.mytemplate.dao.UploadTemplateDao;
import com.mhdanh.mytemplate.domain.UploadTemplate;

@Transactional
@Repository
public class UploadTemplateDaoImpl extends CommonDaoImpl<UploadTemplate> implements UploadTemplateDao{


}
