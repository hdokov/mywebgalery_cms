package com.mywebgalery.cms.pages.modules;

import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import com.mywebgalery.cms.base.BasePage;
import com.mywebgalery.cms.model.Module;

public class ModuleMenu extends BasePage {

	@Property
	private String _username;

	@Environmental
	private Module context;

	@Inject private Request _req;

	@OnEvent(component="loginform")
	public void login(){
		if(!"qwe".equals(_username)){
			getLog().error("invalid username and pass");
			addErrMsg("invalid username and pass - "+_req.getParameter("test"), null);
		}
	}

	public String getVal(){
		return context==null ? "null" : context.getData();
	}
}
