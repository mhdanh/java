package com.mhdanh.mytemplate.service.implement;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mhdanh.mytemplate.domain.Attachment;
import com.mhdanh.mytemplate.domain.Attachment.ATTACHMENT_STATUS;
import com.mhdanh.mytemplate.domain.Attachment.ATTACHMENT_TYPE;
import com.mhdanh.mytemplate.service.AttachmentService;
import com.mhdanh.mytemplate.utility.Utility;

@Service
@Transactional
public class AttachmentServiceImpl extends CommonServiceImpl<Attachment> implements AttachmentService{

	private final static Logger logger = Logger.getLogger(AttachmentServiceImpl.class);
	
	@Autowired
	private Utility utility;
	
	@Override
	public Attachment saveAttachmentFeedback(MultipartFile fileFeedback) {
		try {
			Attachment attachmentFeedback = new Attachment();
			attachmentFeedback.setFileName(fileFeedback.getOriginalFilename());
			attachmentFeedback.setDateModified(new Date());
			attachmentFeedback.setSize(fileFeedback.getSize());
			attachmentFeedback.setStatus(ATTACHMENT_STATUS.OK);
			attachmentFeedback.setType(ATTACHMENT_TYPE.FEEDBACK);
			attachmentFeedback.setUploader(utility.getUserLogined());
			
			byte[] bytesFeedbackFile = fileFeedback.getBytes();
			String folderAttachmentFeedback = utility.getValueFromPropertiesSystemFile("system.url.store.attachment") + ATTACHMENT_TYPE.FEEDBACK.toString();
			File fileFolderAttachmentFeedback = new File(folderAttachmentFeedback);
			if(!fileFolderAttachmentFeedback.exists()) {
				fileFolderAttachmentFeedback.mkdirs();
			}
			Date currentDate = new Date();
			String strFileAttachmentFeedback = folderAttachmentFeedback + "/" + String.valueOf(currentDate.getTime()) + fileFeedback.getOriginalFilename();
			File fileAttachmentFeedback = new File(strFileAttachmentFeedback);
			FileOutputStream outFileAttachmentFeedback = new FileOutputStream(fileAttachmentFeedback);
			outFileAttachmentFeedback.write(bytesFeedbackFile);
			outFileAttachmentFeedback.close();
			attachmentFeedback.setFullLink(strFileAttachmentFeedback);
			add(attachmentFeedback);
			return attachmentFeedback;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error saveAttachmentFeedback ",e);
			return null;
		}
		
	}

	@Override
	public Attachment saveAttachmentContactUs(MultipartFile fileContact) {
		try {
			Attachment attachmentContactUs = new Attachment();
			attachmentContactUs.setFileName(fileContact.getOriginalFilename());
			attachmentContactUs.setDateModified(new Date());
			attachmentContactUs.setSize(fileContact.getSize());
			attachmentContactUs.setStatus(ATTACHMENT_STATUS.OK);
			attachmentContactUs.setType(ATTACHMENT_TYPE.CONTACT);
			attachmentContactUs.setUploader(utility.getUserLogined());
			
			byte[] bytesFeedbackFile = fileContact.getBytes();
			String folderAttachmentFeedback = utility.getValueFromPropertiesSystemFile("system.url.store.attachment") + ATTACHMENT_TYPE.FEEDBACK.toString();
			File fileFolderAttachmentFeedback = new File(folderAttachmentFeedback);
			if(!fileFolderAttachmentFeedback.exists()) {
				fileFolderAttachmentFeedback.mkdirs();
			}
			Date currentDate = new Date();
			String strFileAttachmentFeedback = folderAttachmentFeedback + "/" + String.valueOf(currentDate.getTime()) + fileContact.getOriginalFilename();
			File fileAttachmentFeedback = new File(strFileAttachmentFeedback);
			FileOutputStream outFileAttachmentFeedback = new FileOutputStream(fileAttachmentFeedback);
			outFileAttachmentFeedback.write(bytesFeedbackFile);
			outFileAttachmentFeedback.close();
			attachmentContactUs.setFullLink(strFileAttachmentFeedback);
			add(attachmentContactUs);
			return attachmentContactUs;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error saveAttachmentContactUs ",e);
			return null;
		}
	}
	
}
