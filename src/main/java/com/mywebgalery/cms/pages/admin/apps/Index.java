package com.mywebgalery.cms.pages.admin.apps;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.hibernate.Session;

import com.mywebgalery.cms.base.BasePage;
import com.mywebgalery.cms.model.App;

public class Index extends BasePage{

	private List<App> _apps;

	@Property
	private App _app;

	@OnEvent(value=EventConstants.ACTIVATE)
	public void activate(Object... params){

	}

	public List<App> getApps() {
		if(_apps == null){
			try {
				Session s = getTransactionManager().getSession();
				s.beginTransaction();
				_apps = App.getInstance().findByProperty(s, false, "accountId", getUser().getAccountId());
			} catch (Exception e) {
				getLog().error(e.getMessage(),e);
				addErrMsg(e.getMessage(), null);
			}
			if(_apps == null)
				_apps = new ArrayList<App>();
		}
		return _apps;
	}



}
