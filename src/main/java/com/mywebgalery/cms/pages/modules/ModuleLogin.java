package com.mywebgalery.cms.pages.modules;

import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.hibernate.Session;

import com.mywebgalery.cms.base.BasePage;
import com.mywebgalery.cms.model.Module;
import com.mywebgalery.cms.pages.admin.apps.Modules;

public class ModuleLogin extends BasePage {

	@Property
	private String _username;

	@Property
	private String _password;

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

	@OnEvent(component="form")
	public Object submit(){
		try {
			if(getRequest().getRequest().getParameter("cancel")!=null){
				getTransactionManager().rollback();
			} else {
				Session s = getTransactionManager().getSession();
				s.beginTransaction();
				context.saveOrUpdate(s);
			}
			return Modules.class;
		} catch (Exception e) {
			getLog().error(e.getMessage(),e);
			addErrMsg(e.getMessage(), null);
		}
		return null;
	}

	public Module getModule() {
		return context;
	}
}
