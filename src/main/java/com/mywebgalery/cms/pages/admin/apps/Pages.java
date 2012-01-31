package com.mywebgalery.cms.pages.admin.apps;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.hibernate.Session;

import com.mywebgalery.cms.base.AdminBasePage;
import com.mywebgalery.cms.model.App;
import com.mywebgalery.cms.model.Page;

public class Pages extends AdminBasePage {

	private List<Page> _pages;

	@SuppressWarnings("unused")
	@Property
	private Page _page;

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

	public List<Page> getPages() {
		if(_pages == null){
			try {
				Session s = getTransactionManager().getSession();
				s.beginTransaction();
				_pages = Page.getInstance().getByApp(s, _app.getId());
			} catch (Exception e) {
				getLog().error(e.getMessage(),e);
				addErrMsg(e.getMessage(), null);
			}
			if(_pages == null)
				_pages = new ArrayList<Page>();
		}
		return _pages;
	}

	@OnEvent(component="del")
	public void delete(Long id){
		try {
			Session s = getTransactionManager().getSession();
			s.beginTransaction();
			Page.getInstance().deleteById(s, id);
			s.flush();
		} catch (Exception e) {
			getLog().error(e.getMessage(),e);
			addErrMsg(translate("error.cannot_delete_module"), null);
		}
	}

	@OnEvent(component="default")
	public void setDefault(Long id){
		try {
			Session s = getTransactionManager().getSession();
			s.beginTransaction();
			Page.getInstance().setDefault(s, _app.getId(), id);
			s.flush();
		} catch (Exception e) {
			getLog().error(e.getMessage(),e);
			addErrMsg(translate("error.cannot_delete_module"), null);
		}
	}

	public String getCategory(){
		return "";
	}
}
