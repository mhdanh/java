package com.mhdanh.mytemplate.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "commentTemplate")
public class CommentTemplate {

	@Id
	@GenericGenerator(name = "increment", strategy = "increment")
	@GeneratedValue(generator = "increment")
	@Column(name = "id")
	private int id;

	@Column(columnDefinition = "text")
	private String content;

	@Column
	private Date dateCreated;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account commenter;

	@ManyToOne
	@JoinColumn(name = "template_id")
	private Template template;

	@ManyToOne
	@JoinColumn(name = "parentcomment_id", nullable = true)
	private CommentTemplate parentComment;

	@OneToMany(mappedBy = "parentComment")
	@Cascade(value = CascadeType.DELETE)
	private List<CommentTemplate> childComments;

	public CommentTemplate() {
	}

	/**
	 * 
	 * @return like 3 days ago
	 */
	public String getDateToString() {
		String strDate = "";
		Calendar currentDate = Calendar.getInstance();
		int dayAfterSubtract = (int) Math
				.floor((currentDate.getTimeInMillis() - this.dateCreated
						.getTime()) / (1000 * 60 * 60 * 24));

		if (Math.floor(dayAfterSubtract / 7) < 1) {
			if (dayAfterSubtract == 0) {
				strDate = "Today";
			} else if (dayAfterSubtract == 1) {
				strDate = "Yesterday";
			} else {
				strDate = dayAfterSubtract + " days ago";
			}
		} else if (Math.floor(dayAfterSubtract / 7) >= 1
				&& Math.floor(dayAfterSubtract / 30) < 1) {
			int week = (int) Math.floor(dayAfterSubtract / 7);
			if (week == 1) {
				strDate = "Last week";
			} else {
				strDate = week + " weeks ago";
			}
		} else if (Math.floor(dayAfterSubtract / 30) >= 1
				&& Math.floor(dayAfterSubtract / 365) < 1) {
			int month = (int) Math.floor(dayAfterSubtract / 30);
			if (month == 1) {
				strDate = "Last month";
			} else {
				strDate = month + " months ago";
			}
		} else if (Math.floor(dayAfterSubtract / 365) >= 1) {
			int year = (int) Math.floor(dayAfterSubtract / 365);
			if (year == 1) {
				strDate = "Last year";
			} else {
				strDate = year + " years ago";
			}
		}
		return strDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Account getCommenter() {
		return commenter;
	}

	public void setCommenter(Account commenter) {
		this.commenter = commenter;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public CommentTemplate getParentComment() {
		return parentComment;
	}

	public void setParentComment(CommentTemplate parentComment) {
		this.parentComment = parentComment;
	}

	public List<CommentTemplate> getChildComments() {
		return childComments;
	}

	public void setChildComments(List<CommentTemplate> childComments) {
		this.childComments = childComments;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof CommentTemplate)) {
			return false;
		}
		CommentTemplate otherAccount = (CommentTemplate) o;
		return this.getId() == otherAccount.getId();
	}

	@Override
	public int hashCode() {
		return this.getId();
	}

}
