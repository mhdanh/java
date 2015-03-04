package com.mhdanh.mytemplate.viewmodel;

public class CommentTemplateModel {
	private Integer idTemplate;
	private String contentComment;
	private Integer idCommentParent;

	public CommentTemplateModel() {
	}

	public Integer getIdTemplate() {
		return idTemplate;
	}

	public void setIdTemplate(Integer idTemplate) {
		this.idTemplate = idTemplate;
	}

	public String getContentComment() {
		return contentComment;
	}

	public void setContentComment(String contentComment) {
		this.contentComment = contentComment;
	}

	public Integer getIdCommentParent() {
		return idCommentParent;
	}

	public void setIdCommentParent(Integer idCommentParent) {
		this.idCommentParent = idCommentParent;
	}

}
