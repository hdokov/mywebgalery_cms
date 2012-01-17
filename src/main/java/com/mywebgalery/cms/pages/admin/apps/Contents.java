package com.mywebgalery.cms.pages.admin.apps;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.hibernate.Session;

import com.mywebgalery.cms.base.AdminBasePage;
import com.mywebgalery.cms.model.App;

public class Contents extends AdminBasePage {

	private App _app;

	@OnEvent(value=EventConstants.ACTIVATE)
	public Object activate(Object... params){
		if(params == null || params.length == 0){
			_app = (App)getSessionData().get("current_app");
		} else {
			try {
				Session s = getTransactionManager().getSession();
				s.beginTransaction();
				_app = App.getInstance().findById(s, Long.parseLong(params[0].toString()));
			} catch (Exception e) {
				getLog().error(e.getMessage(),e);
				addErrMsg(e.getMessage(), null);
				return Index.class;
			}
		}
		if(_app == null){
			addErrMsg(translate("error.select_app"), null);
			return Index.class;
		}
		getSessionData().put("current_app", _app);
		return null;
	}

	public App getApp() {
		return _app;
	}


}
