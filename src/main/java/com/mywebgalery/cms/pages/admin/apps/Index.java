package com.mywebgalery.cms.pages.admin.apps;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;

import com.mywebgalery.cms.base.BasePage;

public class Index extends BasePage{

	private String _page;

	@OnEvent(value=EventConstants.ACTIVATE)
	public void activate(Object... params){
		if(params != null && params.length > 0)
			_page = params[0].toString();

		if(_page == null)
			_page = "home";
	}

	public String getTemplate(){
		return "context:WEB-INF/pages/mywebgalery/"+_page+".html";
	}
}
