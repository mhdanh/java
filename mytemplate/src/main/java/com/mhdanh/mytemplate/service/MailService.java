package com.mhdanh.mytemplate.service;

import com.mhdanh.mytemplate.viewmodel.MailSenderDTO;

public interface MailService {
	void sendHtmlMail(MailSenderDTO mailSender);
}
