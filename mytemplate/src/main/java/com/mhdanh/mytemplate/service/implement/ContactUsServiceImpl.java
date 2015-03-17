package com.mhdanh.mytemplate.service.implement;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mhdanh.mytemplate.dao.ContactUsDao;
import com.mhdanh.mytemplate.domain.Attachment;
import com.mhdanh.mytemplate.domain.ContactUs;
import com.mhdanh.mytemplate.domain.ContactUs.CONTACT_STATUS;
import com.mhdanh.mytemplate.domain.Feedback;
import com.mhdanh.mytemplate.domain.Feedback.FEEDBACK_STATUS;
import com.mhdanh.mytemplate.domain.Feedback.FEEDBACK_TYPE;
import com.mhdanh.mytemplate.service.AttachmentService;
import com.mhdanh.mytemplate.service.ContactUsService;
import com.mhdanh.mytemplate.viewmodel.ContactUsDTO;

@Service
@Transactional
public class ContactUsServiceImpl extends CommonServiceImpl<ContactUs> implements ContactUsService{
	
	private final static Logger logger = Logger.getLogger(ContactUsServiceImpl.class);

	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private ContactUsDao contactUsDao;
	
	@Override
	public String contactAdd(ContactUsDTO contactData) {
		try {
			ContactUs contactUs = new ContactUs();
			if(contactData.getFileContact() != null) {
				//save attachment
				Attachment attachment =  attachmentService.saveAttachmentContactUs(contactData.getFileContact());
				if(attachment != null) {
					contactUs.setAttachment(attachment);
				}
			}
			contactUs.setFullName(contactData.getYourNameContact());
			contactUs.setEmail(contactData.getYourEmailContact());
			contactUs.setSubject(contactUs.getSubject());
			contactUs.setContent(contactUs.getContent());
			contactUs.setDateCreated(new Date());
			contactUs.setStatus(CONTACT_STATUS.UNREAD);
			contactUsDao.save(contactUs);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error contactAdd ",e);
		}
		return "redirect:/contact";
	}
	
}
