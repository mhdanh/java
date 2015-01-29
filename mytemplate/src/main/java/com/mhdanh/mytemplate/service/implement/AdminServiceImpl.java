package com.mhdanh.mytemplate.service.implement;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mhdanh.mytemplate.service.AdminService;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	
	
	@Override
	public String initAllTemplateFromZipFile() {
		
		return "Done";
	}
	
}
