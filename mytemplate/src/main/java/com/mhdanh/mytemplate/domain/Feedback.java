package com.mhdanh.mytemplate.domain;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "feedback")
public class Feedback {

	public static enum FEEDBACK_TYPE {
		FEATURE, ERROR, REQUIRE
	}

	public static enum FEEDBACK_STATUS {
		OPEN, CLOSE, FIXED
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

	@OneToOne
	@JoinColumn(name = "attachment_id")
	@Cascade(value = { CascadeType.DELETE_ORPHAN })
	private Attachment attachment;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account feedbacker;

	@ManyToOne()
	private Feedback parentFeedback;

	@OneToMany(mappedBy = "parentFeedback")
	@Cascade(value = {CascadeType.DELETE_ORPHAN})
	private List<Feedback> childsFeedback;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof Feedback)) {
			return false;
		}
		Feedback otherFeedback = (Feedback) o;
		return this.getId() == otherFeedback.getId();
	}

	@Override
	public int hashCode() {
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

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public Feedback getParentFeedback() {
		return parentFeedback;
	}

	public void setParentFeedback(Feedback parentFeedback) {
		this.parentFeedback = parentFeedback;
	}

	public List<Feedback> getChildsFeedback() {
		//add comparator
		Comparator<Feedback> orderDateDesc = new Comparator<Feedback>() {
			@Override
			public int compare(Feedback fb, Feedback fbOther) {
				return fbOther.getDateCreated().compareTo(fb.getDateCreated());
			}
		};
		if(!childsFeedback.isEmpty()) {
			Collections.sort(childsFeedback,orderDateDesc);
		}
		return childsFeedback;
	}

	public void setChildsFeedback(List<Feedback> childsFeedback) {
		this.childsFeedback = childsFeedback;
	}

	public Account getFeedbacker() {
		return feedbacker;
	}

	public void setFeedbacker(Account feedbacker) {
		this.feedbacker = feedbacker;
	}

}
