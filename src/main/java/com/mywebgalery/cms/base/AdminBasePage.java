package com.mywebgalery.cms.base;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;

import com.mywebgalery.cms.model.User;

public class AdminBasePage extends BasePage {

	@OnEvent(value=EventConstants.ACTIVATE)
	public Object validateSession(){
		if(getSessionData().getUser() == null){
			getLog().info("Unauthorized access.");
			return "index";
		}
		return null;
	}

}
