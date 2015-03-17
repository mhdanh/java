package com.mhdanh.mytemplate.service.implement;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
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
import com.mhdanh.mytemplate.service.MailService;
import com.mhdanh.mytemplate.viewmodel.ContactUsDTO;
import com.mhdanh.mytemplate.viewmodel.HardCode;
import com.mhdanh.mytemplate.viewmodel.MailSenderDTO;

@Service
@Transactional
public class ContactUsServiceImpl extends CommonServiceImpl<ContactUs> implements ContactUsService{
	
	private final static Logger logger = Logger.getLogger(ContactUsServiceImpl.class);

	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private ContactUsDao contactUsDao;
	@Autowired
	private MailService mailService;;
	
	@Override
	public String contactAdd(ContactUsDTO contactData) {
		try {
			//send mail to maidanhcongtu
			MailSenderDTO sendMailToAdmin = new MailSenderDTO();
			sendMailToAdmin.setTo(HardCode.adminMail);
			sendMailToAdmin.setSubject(contactData.getSubjectContact());
			sendMailToAdmin.setContent(contactData.getYourEmailContact() + "("+contactData.getYourEmailContact()+")" + "<br/>" + contactData.getContentContact());
			ContactUs contactUs = new ContactUs();
			if(contactData.getFileContact() != null) {
				//save attachment
				Attachment attachment =  attachmentService.saveAttachmentContactUs(contactData.getFileContact());
				if(attachment != null) {
					contactUs.setAttachment(attachment);
					sendMailToAdmin.setAttachmentName(attachment.getFileName());
					sendMailToAdmin.setLinkAttachment(attachment.getFullLink());
				}
			}
			contactUs.setFullName(contactData.getYourNameContact());
			contactUs.setEmail(contactData.getYourEmailContact());
			contactUs.setSubject(contactData.getSubjectContact());
			contactUs.setContent(contactData.getContentContact());
			contactUs.setDateCreated(new Date());
			contactUs.setStatus(CONTACT_STATUS.UNREAD);
			contactUsDao.save(contactUs);
			//send mail
			mailService.sendHtmlMail(sendMailToAdmin);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error contactAdd ",e);
		}
		return "redirect:/contact";
	}
	
}
