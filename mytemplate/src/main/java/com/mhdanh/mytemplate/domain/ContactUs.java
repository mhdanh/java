package com.mhdanh.mytemplate.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ContactUs")
public class ContactUs {

	public static enum CONTACT_STATUS {
		UNREAD, READ
	}

	@Id
	@GenericGenerator(name = "increment", strategy = "increment")
	@GeneratedValue(generator = "increment")
	@Column(name = "id")
	private int id;

	@Column
	private String fullName;

	@Column
	private String email;

	@Column
	private String subject;

	@Column(columnDefinition = "text")
	private String content;

	@Column
	private CONTACT_STATUS status;

	@Column
	private Date dateCreated;

	@ManyToOne
	@JoinColumn(name = "attachment_id")
	private Attachment attachment;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof ContactUs)) {
			return false;
		}
		ContactUs otherContactUs = (ContactUs) o;
		return this.getId() == otherContactUs.getId();
	}

	@Override
	public int hashCode() {
		return this.getId();
	}

	public ContactUs() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public CONTACT_STATUS getStatus() {
		return status;
	}

	public void setStatus(CONTACT_STATUS status) {
		this.status = status;
	}

}
