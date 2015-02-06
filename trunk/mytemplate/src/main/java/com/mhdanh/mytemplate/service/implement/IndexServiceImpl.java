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
import com.mhdanh.mytemplate.viewmodel.HardCode;
import com.mhdanh.mytemplate.viewmodel.LazyLoadTemplateFilterIndex;

@Service
@Transactional
public class IndexServiceImpl implements IndexService{

	@Autowired
	private TemplateService templateService;
	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public String indexPage(Model model,LazyLoadTemplateFilterIndex lazyLoadingTemplate) {
		lazyLoadingTemplate = defaultLazyLoadingCategory(lazyLoadingTemplate);
		List<Template> templates = templateService.getLazyTemplatePublished(lazyLoadingTemplate);
		int totalTemplatePublished = templateService.countTotalTemplatePublished();
		List<Category> categories = categoryDao.getAll();
		List<FilterModel> filters = createFilters();
		model.addAttribute("filters", filters);
		model.addAttribute("templates", templates);
		model.addAttribute("categories", categories);
		model.addAttribute("lazyLoading",lazyLoadingTemplate);
		model.addAttribute("totalTemplatePublished", totalTemplatePublished);
		return "/index";
	}

	private LazyLoadTemplateFilterIndex defaultLazyLoadingCategory(LazyLoadTemplateFilterIndex lazyLoadingTemplate) {
		if(lazyLoadingTemplate.getStep() == 0){
			int itemOnPage = HardCode.itemOnPageIndex;
			lazyLoadingTemplate.setStep(itemOnPage);
		}
		if(lazyLoadingTemplate.getValueFilter() == null){
			lazyLoadingTemplate.setValueFilter("");
		}
		return lazyLoadingTemplate;
	}

	private List<FilterModel> createFilters() {
		List<FilterModel> filters = new ArrayList<FilterModel>();
		FilterModel filterTopDownload = new FilterModel();
		filterTopDownload.setValue(HardCode.topDownload);
		filterTopDownload.setDisplayName("Top download");
		filters.add(filterTopDownload);
		
		FilterModel filterTopFree = new FilterModel();
		filterTopFree.setValue(HardCode.topFreeDownload);
		filterTopFree.setDisplayName("Top free download");
		filters.add(filterTopFree);
		
		FilterModel filterTopPremium = new FilterModel();
		filterTopPremium.setValue(HardCode.topPremiumDownload);
		filterTopPremium.setDisplayName("Top premium download");
		filters.add(filterTopPremium);
		
		FilterModel filterFreeNewest = new FilterModel();
		filterFreeNewest.setValue(HardCode.FreeNewest);
		filterFreeNewest.setDisplayName("Template Free Newest");
		filters.add(filterFreeNewest);
		
		FilterModel filterPremiumNewest = new FilterModel();
		filterPremiumNewest.setValue(HardCode.PremiumNewest);
		filterPremiumNewest.setDisplayName("Template Premium Newest");
		filters.add(filterPremiumNewest);
		
		return filters;
	}

	@Override
	public String indexLoadMore(Model model,
			LazyLoadTemplateFilterIndex valueFilter) {
		int nextPage = valueFilter.getPage() + 1;
		valueFilter.setPage(nextPage);
		List<Template> templates = templateService.getLazyTemplatePublished(valueFilter);
		int totalTemplatePublished = templateService.countTotalTemplatePublished();
		model.addAttribute("templates", templates);
		model.addAttribute("lazyLoading", valueFilter);
		model.addAttribute("totalTemplatePublished", totalTemplatePublished);
		return "/ajax/index-load-more";
	}

}
