package com.mhdanh.mytemplate.service;

public interface GooglePlusService {

	String authGoogle();

	String authGoogleCallBack(String code, String error);

}
