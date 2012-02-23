package com.mywebgalery.cms.pages.admin.apps;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.services.Request;
import org.hibernate.Session;

import com.mywebgalery.cms.base.AdminBasePage;
import com.mywebgalery.cms.model.App;
import com.mywebgalery.cms.model.Category;
import com.mywebgalery.cms.utils.StringUtils;

public class Categories extends AdminBasePage {

	private List<Category> _categories;

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

	public List<Category> getCategories() {
		if(_categories == null){
			try {
				Session s = getTransactionManager().getSession();
				s.beginTransaction();
				_categories = Category.getInstance().getRootCategories(s, _app.getId());
			} catch (Exception e) {
				getLog().error(e.getMessage(),e);
				addErrMsg(e.getMessage(), null);
			}
			if(_categories == null)
				_categories = new ArrayList<Category>();
		}
		return _categories;
	}

	@OnEvent(component="edit")
	public void edit(){
		Request req = getRequest().getRequest();
		String name = req.getParameter("name");
		String id = req.getParameter("id");
		String parent = req.getParameter("parent");
		try{
			Session s = getTransactionManager().getSession();
			s.beginTransaction();
			if(StringUtils.isBlank(id)){
				Category c = new Category();
				c.setAppId(_app.getId());
				c.setName(name);
				if(StringUtils.isBlank(parent)){
					c.save(s);
				}
			}
			s.flush();
		} catch (Exception e) {
			getLog().error(e.getMessage(),e);
			addErrMsg(e.getMessage(), null);
		}
	}

}
