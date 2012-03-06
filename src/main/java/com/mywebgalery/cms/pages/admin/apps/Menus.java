package com.mywebgalery.cms.pages.admin.apps;

import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.Renderable;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.services.Request;
import org.hibernate.Session;

import com.mywebgalery.cms.base.AdminBasePage;
import com.mywebgalery.cms.model.App;
import com.mywebgalery.cms.model.Menu;
import com.mywebgalery.cms.model.Page;
import com.mywebgalery.cms.model.helpers.CategoryPageBean;
import com.mywebgalery.cms.utils.StringUtils;

public class Menus extends AdminBasePage {

	private Menu _root;

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

	public Menu getRoot() {
		if(_root == null){
			try {
				Session s = getTransactionManager().getSession();
				s.beginTransaction();
				_root = Menu.getInstance().getRootMenu(s, _app.getId());
			} catch (Exception e) {
				getLog().error(e.getMessage(),e);
				addErrMsg(e.getMessage(), null);
			}
			if(_root == null)
				_root = new Menu();
		}
		return _root;
	}

	@OnEvent(component="edit")
	public void edit(){
		Request req = getRequest().getRequest();
		String name = req.getParameter("name");
		String id = req.getParameter("id");
		String parent = req.getParameter("parent");
		String page = req.getParameter("page");
		try{
			Session s = getTransactionManager().getSession();
			s.beginTransaction();
			if(StringUtils.isBlank(id)){
				Menu c = new Menu();
				c.setAppId(_app.getId());
				c.setName(name);
				c.setUri(page);
				Menu p = Menu.getInstance().get(s, Long.parseLong(parent));
				if(p == null){
					s.getTransaction().rollback();
					addErrMsg(translate("error.invalid_request"), null);
					return;
				}
				//c.setParentMenu(p.getId());
				p.addSubMenu(c);

				p.save(s);
				//c.save(s);
			} else {
				Menu c = Menu.getInstance().get(s, Long.parseLong(id));
				c.setName(name);
				c.setUri(page);
				if(c == null){
					s.getTransaction().rollback();
					addErrMsg(translate("error.invalid_request"), null);
					return;
				}
				Menu p = Menu.getInstance().get(s, Long.parseLong(parent));
				if(p == null){
					s.getTransaction().rollback();
					addErrMsg(translate("error.invalid_request"), null);
					return;
				}
				if(!p.equals(c.getParent())){
					c.setParent(p);
					p.addSubMenu(c);
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

	public Renderable getPageOptions(){
		return new Renderable() {
			public void render(MarkupWriter w) {
				Session s = getTransactionManager().getSession();
				s.beginTransaction();
				List<CategoryPageBean> pages = Page.getInstance().getPagesList(s, _app.getId());
				String category = null;
				boolean groupStarted = false;
				for(CategoryPageBean c : pages){
					if(!c.getCategoryName().equals(category)){
						if(groupStarted){
							w.end();
							groupStarted = false;
						}
						category = c.getCategoryName();
						if(!"root".equals(category)){
							groupStarted = true;
							w.element("optgroup", "label", category);
						}
					}
					if(c.getPageUrl() != null){
						w.element("option", "value", c.getPageUrl());
						w.write(c.getPageName());
						w.end();
					}
				}
				if(groupStarted)
					w.end();
			}
		};
	}
}
