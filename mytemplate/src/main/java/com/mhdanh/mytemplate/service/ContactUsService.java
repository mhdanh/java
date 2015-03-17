package com.mhdanh.mytemplate.service;

import com.mhdanh.mytemplate.domain.ContactUs;
import com.mhdanh.mytemplate.viewmodel.ContactUsDTO;

public interface ContactUsService extends CommonService<ContactUs> {

	String contactAdd(ContactUsDTO contactData);

}
