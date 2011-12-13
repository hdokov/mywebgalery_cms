package com.mywebgalery.cms.base;

import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;


public class BasePage extends BaseComponent{

	@Inject
	private RequestGlobals _request;

	@CleanupRender
	public void cleanup(){
		getAppMessages().clear();
	}

	public RequestGlobals getRequest() {
		return _request;
	}


}
