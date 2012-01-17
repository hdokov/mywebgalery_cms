package com.mywebgalery.cms.pages.modules;

import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;

import com.mywebgalery.cms.base.BasePage;
import com.mywebgalery.cms.model.Module;

public class ModuleHtml extends BasePage {

	@Property
	private String _html;

	@Environmental
	private Module context;

	@OnEvent(component="htmlform")
	public void submit(){
		context.setContent(_html);
	}

	public String getHtml(){
		return context.getContent();
	}

}
