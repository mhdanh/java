package com.mhdanh.mytemplate.viewmodel;

import org.springframework.web.multipart.MultipartFile;

public class FeedbackDTO {

	private String subjectFeedback;
	private String contentFeedback;
	private MultipartFile fileFeedback;
	private String typeFeedback;
	private Integer idParentFeedback;

	public FeedbackDTO() {
	}

	public String getSubjectFeedback() {
		return subjectFeedback;
	}

	public void setSubjectFeedback(String subjectFeedback) {
		this.subjectFeedback = subjectFeedback;
	}

	public String getContentFeedback() {
		return contentFeedback;
	}

	public void setContentFeedback(String contentFeedback) {
		this.contentFeedback = contentFeedback;
	}

	public MultipartFile getFileFeedback() {
		return fileFeedback;
	}

	public void setFileFeedback(MultipartFile fileFeedback) {
		this.fileFeedback = fileFeedback;
	}

	public String getTypeFeedback() {
		return typeFeedback;
	}

	public void setTypeFeedback(String typeFeedback) {
		this.typeFeedback = typeFeedback;
	}

	public Integer getIdParentFeedback() {
		return idParentFeedback;
	}

	public void setIdParentFeedback(Integer idParentFeedback) {
		this.idParentFeedback = idParentFeedback;
	}
	
}
