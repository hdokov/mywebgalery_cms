package com.mywebgalery.cms.pages.admin.apps;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.hibernate.Session;

import com.mywebgalery.cms.base.AdminBasePage;
import com.mywebgalery.cms.model.App;
import com.mywebgalery.cms.model.Module;

public class Pages extends AdminBasePage {

	private List<Module> _modules;

	@SuppressWarnings("unused")
	@Property
	private Module _module;

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

	public List<Module> getModules() {
		if(_modules == null){
			try {
				Session s = getTransactionManager().getSession();
				s.beginTransaction();
				_modules = Module.getInstance().findByProperty(s, false, "appId", _app.getId());
			} catch (Exception e) {
				getLog().error(e.getMessage(),e);
				addErrMsg(e.getMessage(), null);
			}
			if(_modules == null)
				_modules = new ArrayList<Module>();
		}
		return _modules;
	}

	@OnEvent(component="delmodule")
	public void delete(Long id){
		try {
			Session s = getTransactionManager().getSession();
			s.beginTransaction();
			Module.getInstance().deleteById(s, id);
			s.flush();
		} catch (Exception e) {
			getLog().error(e.getMessage(),e);
			addErrMsg(translate("error.cannot_delete_module"), null);
		}
	}


}
