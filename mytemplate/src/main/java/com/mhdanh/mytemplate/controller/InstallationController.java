package com.mhdanh.mytemplate.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;





import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.domain.Category;
import com.mhdanh.mytemplate.domain.Role;
import com.mhdanh.mytemplate.domain.Role.ROLE_NAME;
import com.mhdanh.mytemplate.service.AccountService;
import com.mhdanh.mytemplate.service.CategoryService;
import com.mhdanh.mytemplate.service.RoleService;
import com.mhdanh.mytemplate.utility.Utility;

@Controller
public class InstallationController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private Utility utility;
	
	private final Logger logger = Logger.getLogger(InstallationController.class);
	
	@RequestMapping("/installation/insert-data-sample")
	@ResponseBody
	public Object insertDataSample(){
		try {
			logger.warn("*********insert category sample************");
			
			Category categoryAdminDashboard = new Category("CATEGORY.ADMIN_DASHBOARD");
			categoryService.add(categoryAdminDashboard);
			
			Category categoryLandingPage = new Category("CATEGORY.LANDING_PAGES");
			categoryService.add(categoryLandingPage);
			
			Category categoryBussinessCorporate= new Category("CATEGORY.BUSSINESS_CORPORATE");
			categoryService.add(categoryBussinessCorporate);
			
			Category categoryComponent= new Category("CATEGORY.COMPONENT");
			categoryService.add(categoryComponent);
			
			Category categoryWordPress= new Category("CATEGORY.WORKPRESS");
			categoryService.add(categoryWordPress);
			
			Category categoryOther= new Category("CATEGORY.OTHER");
			categoryService.add(categoryOther);
			
			logger.warn("********end insert category sample*************");
			
			logger.warn("*********insert role sample************");
			Role roleAdmin = new Role();
			roleAdmin.setName(ROLE_NAME.ADMIN);
			roleService.add(roleAdmin);
			
			Role supperUserRole = new Role();
			supperUserRole.setName(ROLE_NAME.SUPPER_USER);
			roleService.add(supperUserRole);
			
			Role roleUser = new Role();
			roleUser.setName(ROLE_NAME.USER);
			roleService.add(roleUser);
			logger.warn("********end insert role sample*************");
			
			logger.warn("*********insert account sample************");
			Account adminAccount = new Account();
			adminAccount.setFirstName("Administrator");
			adminAccount.setUsername("admin");
			String passwordHashed = utility.hashStringWithDefaultKey("123");
			adminAccount.setPassword(passwordHashed);
			List<Role> allRole = roleService.getAll();
			adminAccount.setRoles(allRole);
			accountService.add(adminAccount);
			logger.warn("*********end account sample************");

			
			
		} catch (Exception e) {
			logger.error("insert data sample error",e);
		}
		return "Done";
	}
}
