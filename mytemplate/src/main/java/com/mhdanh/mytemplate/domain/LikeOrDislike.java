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
@Table(name = "like_dislike")
public class LikeOrDislike {
	
	public static enum LIKE_DISLIKE_TYPE {LIKE, DISLIKE}
	
	@Id
	@GenericGenerator(name = "increment", strategy = "increment")
	@GeneratedValue(generator = "increment")
	@Column(name = "id")
	private int id;

	@Column
	@Enumerated(EnumType.STRING)
	private LIKE_DISLIKE_TYPE type;

	@ManyToOne
	private Feedback forFeedback;
	
	@ManyToOne
	private CommentTemplate forCommentTemplate;
	
	@ManyToOne
	private Account liker;
	
	@Column
	private Date dateCreated;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof LikeOrDislike)) {
			return false;
		}
		LikeOrDislike otherLikeOrDislike = (LikeOrDislike) o;
		return this.getId() == otherLikeOrDislike.getId();
	}

	@Override
	public int hashCode() {
		return this.getId();
	}

	public LikeOrDislike() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LIKE_DISLIKE_TYPE getType() {
		return type;
	}

	public void setType(LIKE_DISLIKE_TYPE type) {
		this.type = type;
	}

	public Feedback getForFeedback() {
		return forFeedback;
	}

	public void setForFeedback(Feedback forFeedback) {
		this.forFeedback = forFeedback;
	}

	public CommentTemplate getForCommentTemplate() {
		return forCommentTemplate;
	}

	public void setForCommentTemplate(CommentTemplate forCommentTemplate) {
		this.forCommentTemplate = forCommentTemplate;
	}

	public Account getLiker() {
		return liker;
	}

	public void setLiker(Account liker) {
		this.liker = liker;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
}
