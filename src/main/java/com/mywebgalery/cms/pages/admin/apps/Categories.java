package com.mywebgalery.cms.pages.admin.apps;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.services.Request;
import org.hibernate.Session;

import com.mywebgalery.cms.base.AdminBasePage;
import com.mywebgalery.cms.model.App;
import com.mywebgalery.cms.model.Category;
import com.mywebgalery.cms.utils.StringUtils;

public class Categories extends AdminBasePage {

	private Category _root;

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

	public Category getRoot() {
		if(_root == null){
			try {
				Session s = getTransactionManager().getSession();
				s.beginTransaction();
				_root = Category.getInstance().getRootCategory(s, _app.getId());
			} catch (Exception e) {
				getLog().error(e.getMessage(),e);
				addErrMsg(e.getMessage(), null);
			}
			if(_root == null)
				_root = new Category();
		}
		return _root;
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
				Category p = Category.getInstance().get(s, Long.parseLong(parent));
				if(p == null){
					s.getTransaction().rollback();
					addErrMsg(translate("error.invalid_request"), null);
					return;
				}
				//c.setParentCategory(p.getId());
				p.addSubCategory(c);

				p.save(s);
				//c.save(s);
			} else {
				Category c = Category.getInstance().get(s, Long.parseLong(id));
				c.setName(name);
				if(c == null){
					s.getTransaction().rollback();
					addErrMsg(translate("error.invalid_request"), null);
					return;
				}
				Category p = Category.getInstance().get(s, Long.parseLong(parent));
				if(p == null){
					s.getTransaction().rollback();
					addErrMsg(translate("error.invalid_request"), null);
					return;
				}
				if(p.getId() != c.getParentCategory()){
					c.setParentCategory(p.getId());
					p.addSubCategory(c);
					p.save(s);
				} else {
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
