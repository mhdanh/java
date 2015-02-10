package com.mhdanh.mytemplate.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "feedback")
public class Feedback {

	public static enum FEEDBACK_TYPE {
		FEATURE, ERROR, REQUIRE
	}
	
	public static enum FEEDBACK_STATUS {
		UNREAD, READ, IMPORTANT, HIDE
	}

	@Id
	@GenericGenerator(name = "increment", strategy = "increment")
	@GeneratedValue(generator = "increment")
	@Column(name = "id")
	private int id;

	@Column
	private String subject;

	@Column(columnDefinition = "text")
	private String content;

	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private FEEDBACK_TYPE type;

	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private FEEDBACK_STATUS status;
	
	@Column
	private Date dateCreated;

	@Override
	public boolean equals(Object o){
		if(o == null) {
			return false;
		}
		if(!(o instanceof Feedback)) {
			return false;
		}
		Feedback otherFeedback = (Feedback) o;
		return this.getId() == otherFeedback.getId();
	}
	
	@Override
	public int hashCode(){
		return this.getId();
	}
	
	public Feedback() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public FEEDBACK_TYPE getType() {
		return type;
	}

	public void setType(FEEDBACK_TYPE type) {
		this.type = type;
	}

	public FEEDBACK_STATUS getStatus() {
		return status;
	}

	public void setStatus(FEEDBACK_STATUS status) {
		this.status = status;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
}
