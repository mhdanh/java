package com.mhdanh.mytemplate.domain;

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
