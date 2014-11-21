package com.mhdanh.mytemplate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping(value={"/index","/","/index/"})
	public String index(){
		System.out.println("ok man");
		return "indexIndex";
	}
}
