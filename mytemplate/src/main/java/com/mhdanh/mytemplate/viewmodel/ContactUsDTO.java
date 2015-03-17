package com.mhdanh.mytemplate.viewmodel;

import org.springframework.web.multipart.MultipartFile;

public class ContactUsDTO {

	private String yourNameContact;
	private String yourEmailContact;
	private String subjectContact;
	private String contentContact;
	private MultipartFile fileContact;

	public ContactUsDTO() {
	}

	public String getYourNameContact() {
		return yourNameContact;
	}

	public void setYourNameContact(String yourNameContact) {
		this.yourNameContact = yourNameContact;
	}

	public String getYourEmailContact() {
		return yourEmailContact;
	}

	public void setYourEmailContact(String yourEmailContact) {
		this.yourEmailContact = yourEmailContact;
	}

	public String getSubjectContact() {
		return subjectContact;
	}

	public void setSubjectContact(String subjectContact) {
		this.subjectContact = subjectContact;
	}

	public String getContentContact() {
		return contentContact;
	}

	public void setContentContact(String contentContact) {
		this.contentContact = contentContact;
	}

	public MultipartFile getFileContact() {
		return fileContact;
	}

	public void setFileContact(MultipartFile fileContact) {
		this.fileContact = fileContact;
	}

}
