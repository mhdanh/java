package com.mhdanh.mytemplate.dao.implement;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.mhdanh.mytemplate.dao.AttachmentDao;
import com.mhdanh.mytemplate.domain.Attachment;

@Transactional
@Repository
public class AttachmentDaoImpl extends CommonDaoImpl<Attachment> implements AttachmentDao{


}
