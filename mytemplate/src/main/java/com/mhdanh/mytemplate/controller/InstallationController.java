package com.mhdanh.mytemplate.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mhdanh.mytemplate.domain.Category;
import com.mhdanh.mytemplate.service.CategoryService;

@Controller
public class InstallationController {

	@Autowired
	CategoryService categoryService;
	
	private final Logger logger = Logger.getLogger(InstallationController.class);
	
	@RequestMapping("/installation/insert-data-sample")
	@ResponseBody
	private Object insertDataSample(){
		try {
			logger.debug("insert category sample");
			
			Category categoryAdminDashboard = new Category("ADMIN_DASHBOARD");
			categoryService.add(categoryAdminDashboard);
			
			Category categoryLandingPage = new Category("LANDING_PAGES");
			categoryService.add(categoryLandingPage);
			
			Category categoryBussinessCorporate= new Category("BUSSINESS_CORPORATE");
			categoryService.add(categoryBussinessCorporate);
			
			Category categoryComponent= new Category("COMPONENT");
			categoryService.add(categoryComponent);
			
			Category categoryWordPress= new Category("WORKPRESS");
			categoryService.add(categoryWordPress);
			
			Category categoryOther= new Category("OTHER");
			categoryService.add(categoryOther);
			
			logger.debug("end insert category sample");
		} catch (Exception e) {
			logger.error("insert data sample error",e);
		}
		return "Done";
	}
}
