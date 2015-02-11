package com.mhdanh.mytemplate.service.implement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mhdanh.mytemplate.domain.Category;
import com.mhdanh.mytemplate.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl extends CommonServiceImpl<Category> implements CategoryService{
	
}
