package com.mhdanh.mytemplate.service;

import org.springframework.ui.Model;

import com.mhdanh.mytemplate.viewmodel.LazyLoadTemplateFilterIndex;

public interface IndexService {

	String indexPage(Model model, LazyLoadTemplateFilterIndex valueFilter);

}
