package com.mhdanh.mytemplate.dao;

import java.util.List;

import com.mhdanh.mytemplate.domain.CommentTemplate;
import com.mhdanh.mytemplate.domain.Template;

public interface CommentTemplateDao extends CommonDao<CommentTemplate>{

	List<CommentTemplate> getCommentsParentByTemplate(Template templateById);
	
}
