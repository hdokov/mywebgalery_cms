package com.mywebgalery.cms.pages.admin.apps;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.hibernate.Session;

import com.mywebgalery.cms.base.AdminBasePage;
import com.mywebgalery.cms.model.App;

public class Design extends AdminBasePage {

	private App _app;

	@OnEvent(value=EventConstants.ACTIVATE)
	public Object activate(Object... params){
		_app = (App)getSessionData().get("current_app");
		if(_app == null){
			addErrMsg(translate("error.select_app"), null);
			return Index.class;
		}
		return null;
	}

	public App getApp() {
		return _app;
	}

	@OnEvent(value="clearlogo")
	public void clearLogo(){
		_app.setLogo(null);
		try {
			Session s = getTransactionManager().getSession();
			s.beginTransaction();
			_app.update(s);
		} catch (Exception e) {
			addErrMsg(translate("error.cannot_clear_logo"), null);
			getLog().error(e.getMessage(), e);
		}
	}

	@OnEvent(value="selectlogo")
	public void selectLogo(Object... path){
		StringBuffer logo = new StringBuffer("/uploads/");
		logo.append(_app.getName());
		for(Object s:path){
			logo.append('/');
			logo.append(s);
		}
		_app.setLogo(logo.toString());
		try {
			Session s = getTransactionManager().getSession();
			s.beginTransaction();
			_app.update(s);
		} catch (Exception e) {
			addErrMsg(translate("error.cannot_clear_logo"), null);
			getLog().error(e.getMessage(), e);
		}
	}

}
