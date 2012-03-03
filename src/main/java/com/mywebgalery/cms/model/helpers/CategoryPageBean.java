package com.mywebgalery.cms.model.helpers;

import java.util.ArrayList;
import java.util.List;

public class CategoryPageBean {

	private String _pageUrl;
	private String _pageName;
	private Long _categoryId;
	private String _categoryName;
	private Long _parent;
	private List<CategoryPageBean> _subcategories = new ArrayList<CategoryPageBean>();
	private List<CategoryPageBean> _pages = new ArrayList<CategoryPageBean>();


	public Long getParent() {
		return _parent;
	}
	public void setParent(Long parent) {
		_parent = parent;
	}
	public List<CategoryPageBean> getSubcategories() {
		return _subcategories;
	}
	public void setSubcategories(List<CategoryPageBean> subcategories) {
		_subcategories = subcategories;
	}
	public List<CategoryPageBean> getPages() {
		return _pages;
	}
	public void setPages(List<CategoryPageBean> pages) {
		_pages = pages;
	}
	public String getPageUrl() {
		return _pageUrl;
	}
	public void setPageUrl(String pageId) {
		_pageUrl = pageId;
	}
	public String getPageName() {
		return _pageName;
	}
	public void setPageName(String pageName) {
		_pageName = pageName;
	}
	public Long getCategoryId() {
		return _categoryId;
	}
	public void setCategoryId(Long categoryId) {
		_categoryId = categoryId;
	}
	public String getCategoryName() {
		return _categoryName;
	}
	public void setCategoryName(String categoryName) {
		_categoryName = categoryName;
	}


}
