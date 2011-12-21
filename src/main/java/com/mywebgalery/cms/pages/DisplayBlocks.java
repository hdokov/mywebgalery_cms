package com.mywebgalery.cms.pages;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;

import com.mywebgalery.cms.base.BasePage;

public class DisplayBlocks extends BasePage {

	@Property
	private String _username;

	@OnEvent(component="loginform")
	public void login(){
		if(!"qwe".equals(_username)){
			getLog().error("invalid username and pass");
			addErrMsg("invalid username and pass", null);
		}
	}
}
