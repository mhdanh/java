package com.mhdanh.mytemplate.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "attachment")
public class Attachment {

	public static enum ATTACHMENT_TYPE {
		FEEDBACK
	}

	public static enum ATTACHMENT_STATUS {
		OK, TEMP
	}

	@Id
	@GenericGenerator(name = "increment", strategy = "increment")
	@GeneratedValue(generator = "increment")
	@Column(name = "id")
	private int id;

	@Column
	private String fileName;

	@Column
	private String fullLink;

	@Column
	private long size;

	@Column
	@Enumerated(EnumType.STRING)
	private ATTACHMENT_TYPE type;

	@Column
	@Enumerated(EnumType.STRING)
	private ATTACHMENT_STATUS status;

	@Column
	private Date dateModified;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account uploader;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof Attachment)) {
			return false;
		}
		Attachment otherAttachment = (Attachment) o;
		return this.getId() == otherAttachment.getId();
	}

	@Override
	public int hashCode() {
		return this.getId();
	}

	public Attachment() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFullLink() {
		return fullLink;
	}

	public void setFullLink(String fullLink) {
		this.fullLink = fullLink;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public ATTACHMENT_TYPE getType() {
		return type;
	}

	public void setType(ATTACHMENT_TYPE type) {
		this.type = type;
	}

	public ATTACHMENT_STATUS getStatus() {
		return status;
	}

	public void setStatus(ATTACHMENT_STATUS status) {
		this.status = status;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public Account getUploader() {
		return uploader;
	}

	public void setUploader(Account uploader) {
		this.uploader = uploader;
	}

}
