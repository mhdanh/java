package com.mhdanh.mytemplate.service.implement;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mhdanh.mytemplate.service.MailService;
import com.mhdanh.mytemplate.utility.Utility;
import com.mhdanh.mytemplate.viewmodel.MailSenderDTO;

@Service
@Transactional
public class MailServiceImpl implements MailService {
	Logger logger = Logger.getLogger(MailServiceImpl.class);
	
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private Utility utility;

	@Override
	public void sendHtmlMail(MailSenderDTO mailSender) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper message = new MimeMessageHelper(
					mimeMessage,true, "UTF-8");
			message.setFrom(utility.getValueFromPropertiesSystemFile("system.email.username"));
			message.setTo(new InternetAddress(mailSender.getTo()));
			message.setSubject(mailSender.getSubject());
			message.setText(mailSender.getContent(), true);
			if(mailSender.getAttachmentName() != null && !mailSender.getAttachmentName().isEmpty()){
				File attachFileForMail = new File(mailSender.getLinkAttachment());
				message.addAttachment(mailSender.getAttachmentName(),attachFileForMail);
			}
			javaMailSender.send(mimeMessage);
		} catch (AddressException e) {
			e.printStackTrace();
			logger.error("address error",e);
		} catch (MessagingException e) {
			e.printStackTrace();
			logger.error("mail message error",e);
		}

	}

}
