package com.mywebgalery.cms.base;

import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;

import com.mywebgalery.cms.model.User;


public class BasePage extends BaseComponent{

	@Inject private RequestGlobals _request;
	@Inject private Messages _messages;

	@CleanupRender
	public void cleanup(){
		getAppMessages().clear();
	}

	public RequestGlobals getRequest() {
		return _request;
	}

	public User getLoggedUser(){
		if(getSessionData().getUser() == null){
			User u = new User();
			u.setName(_messages.get("application.guest"));
			getSessionData().setUser(u);
		}
		return getSessionData().getUser();
	}

	public Messages getMessages() {
		return _messages;
	}

}
