package com.mhdanh.mytemplate.utility;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.domain.Category;
import com.mhdanh.mytemplate.service.AccountService;
import com.mhdanh.mytemplate.service.CategoryService;

@Component
public class ElFunctions {
	
	public static List<Category> getCategories(){
		CategoryService categoryService = SpringContext.getApplicationContext().getBean(CategoryService.class);
		return categoryService.getAll();
	}
	
	public static Account getUserLogined(){
		Utility utility = SpringContext.getApplicationContext().getBean(Utility.class);
	    return utility.getUserLogined();
	}
}
