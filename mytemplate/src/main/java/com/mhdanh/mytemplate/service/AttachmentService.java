package com.mhdanh.mytemplate.service;

import org.springframework.web.multipart.MultipartFile;

import com.mhdanh.mytemplate.domain.Attachment;

public interface AttachmentService extends CommonService<Attachment>{

	Attachment saveAttachmentFeedback(MultipartFile fileFeedback);

	Attachment saveAttachmentContactUs(MultipartFile fileContact);

}
