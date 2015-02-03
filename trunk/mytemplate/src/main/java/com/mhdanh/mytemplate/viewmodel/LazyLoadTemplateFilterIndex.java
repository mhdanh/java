package com.mhdanh.mytemplate.viewmodel;

public class LazyLoadTemplateFilterIndex {
	private int idCategory;
	private String valueFilter;
	private int page;
	private int step;

	public LazyLoadTemplateFilterIndex() {
	}

	public int getIdCategory() {
		return idCategory;
	}

	public String getValueFilter() {
		return valueFilter;
	}

	public void setValueFilter(String valueFilter) {
		this.valueFilter = valueFilter;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

}
