package com.mhdanh.mytemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mhdanh.mytemplate.service.IndexService;
import com.mhdanh.mytemplate.viewmodel.LazyLoadTemplateFilterIndex;

@Controller
public class IndexController {
	
	@Autowired
	private IndexService indexService;
	
	@RequestMapping(value={"/index","/","/index/"})
	public String index(Model model,@ModelAttribute("valueFilter") LazyLoadTemplateFilterIndex valueFilter){
		return indexService.indexPage(model,valueFilter);
	}
	
	@RequestMapping(value={"/ajax/index-load-more"})
	public String indexLoadMore(Model model,@ModelAttribute("valueFilter") LazyLoadTemplateFilterIndex valueFilter){
		return indexService.indexLoadMore(model,valueFilter);
	}
}
