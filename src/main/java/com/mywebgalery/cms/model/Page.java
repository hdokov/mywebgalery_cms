package com.mywebgalery.cms.model;

import java.util.List;

public class Page extends Model<Page> {

	private String title;
	private String url;
	private String type;

	private boolean template;

	private List<Module> modules;

}
