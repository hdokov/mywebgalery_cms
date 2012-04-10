package com.mywebgalery.cms.pages.modules;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.hibernate.Session;

import com.mywebgalery.cms.base.ModulePage;
import com.mywebgalery.cms.model.Module;
import com.mywebgalery.cms.model.User;
import com.mywebgalery.cms.pages.admin.apps.Modules;

public class ModuleLogin extends ModulePage {

	@Property
	private String _username;

	@Property
	private String _password;

	@Override
	public void initData() {
		_username = null;
		_password = null;
	}

	@OnEvent(component="loginform")
	public void login(){
		try {
			Session s = getTransactionManager().getSession();
			s.beginTransaction();
			User u = User.getInstance().login(s, _username, _password, false);
			if(u == null){
				addErrMsg(getMessages().get("error.invalid_login"), null);
				return;
			}
			getSessionData().setFrontendUser(u);
		} catch (Exception e) {
			getLog().error(e.getMessage(), e);
			addErrMsg(e.getMessage(), null);
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
				getModule().saveOrUpdate(s);
			}
			return Modules.class;
		} catch (Exception e) {
			getLog().error(e.getMessage(),e);
			addErrMsg(e.getMessage(), null);
		}
		return null;
	}

	public Module getModule() {
		return getModule();
	}
}
