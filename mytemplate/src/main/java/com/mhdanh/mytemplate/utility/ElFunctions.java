package com.mhdanh.mytemplate.utility;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mhdanh.mytemplate.domain.Category;
import com.mhdanh.mytemplate.service.CategoryService;

@Component
public class ElFunctions {
	
	@Autowired
	Utility utility;
	
	public static List<Category> getCategories(){
		CategoryService categoryService = SpringContext.getApplicationContext().getBean(CategoryService.class);
		return categoryService.getAll();
	}
}
