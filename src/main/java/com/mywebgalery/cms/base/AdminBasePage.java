package com.mywebgalery.cms.base;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;

public class AdminBasePage extends BasePage {

	@OnEvent(value=EventConstants.ACTIVATE)
	public Object validateSession(){
		if(getUser().isAnonymous()){
			getLog().info("Unauthorized access.");
			addErrMsg(translate("error.session_expired"), null);
			return "admin/login";
		}
		return null;
	}

}
