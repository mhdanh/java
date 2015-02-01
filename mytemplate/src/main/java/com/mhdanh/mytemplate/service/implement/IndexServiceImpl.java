package com.mhdanh.mytemplate.service.implement;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.mhdanh.mytemplate.dao.CategoryDao;
import com.mhdanh.mytemplate.domain.Category;
import com.mhdanh.mytemplate.domain.Template;
import com.mhdanh.mytemplate.service.IndexService;
import com.mhdanh.mytemplate.service.TemplateService;
import com.mhdanh.mytemplate.viewmodel.FilterModel;

@Service
@Transactional
public class IndexServiceImpl implements IndexService{

	@Autowired
	private TemplateService templateService;
	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public String indexPage(Model model) {
		List<Template> templates = templateService.getAllTemplatePublished();
		List<Category> categories = categoryDao.getAll();
		List<FilterModel> filters = createFilters();
		model.addAttribute("filters", filters);
		model.addAttribute("templates", templates);
		model.addAttribute("categories", categories);
		
		return "/index";
	}

	private List<FilterModel> createFilters() {
		List<FilterModel> filters = new ArrayList<FilterModel>();
		FilterModel filterTopDownload = new FilterModel();
		filterTopDownload.setValue("buy");
		filterTopDownload.setDisplayName("Top download");
		filters.add(filterTopDownload);
		
		FilterModel filterTopFree = new FilterModel();
		filterTopFree.setValue("buy-min");
		filterTopFree.setDisplayName("Top free download");
		filters.add(filterTopFree);
		
		FilterModel filterTopPremium = new FilterModel();
		filterTopPremium.setValue("buy-max");
		filterTopPremium.setDisplayName("Top premium download");
		filters.add(filterTopPremium);
		
		FilterModel filterNewest = new FilterModel();
		filterNewest.setValue("dateModified");
		filterNewest.setDisplayName("Template Newest");
		filters.add(filterNewest);
		
		return filters;
	}

}
