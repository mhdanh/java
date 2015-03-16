package com.mhdanh.mytemplate.dao;

import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.domain.Feedback;
import com.mhdanh.mytemplate.domain.LikeOrDislike;

public interface LikeOrDislikeDao extends CommonDao<LikeOrDislike>{
	public LikeOrDislike getById(int idLikeOrDislike);
	public LikeOrDislike getByLikerAndFeedback(Account liker,Feedback feedback);
}
