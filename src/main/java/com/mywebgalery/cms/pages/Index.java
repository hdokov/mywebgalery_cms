package com.mywebgalery.cms.pages;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanBlockSource;


/**
 * Start page of application cms.
 */
public class Index {

	@Inject private BeanBlockSource _blockSource;

	private String _theme = "base";

	public String getTitle(){
		return "title string";
	}

	public String getTemplate(){
		return String.format("context:templates/%s/template.html", _theme);
	}
	public String getTemplateCss(){
		return String.format("/templates/%s/template.css", _theme);
	}
	public String getTemplateJs(){
		return String.format("/templates/%s/template.js", _theme);
	}

	public Block getBlock(){
		return _blockSource.getDisplayBlock("module.login");
	}

	public String getDynamicCss(){
		return "body {background:#eee;}";
	}

}
