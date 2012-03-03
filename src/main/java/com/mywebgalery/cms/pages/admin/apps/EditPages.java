package com.mywebgalery.cms.pages.admin.apps;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.mywebgalery.cms.base.AdminBasePage;
import com.mywebgalery.cms.model.App;
import com.mywebgalery.cms.model.Category;
import com.mywebgalery.cms.model.Page;
import com.mywebgalery.cms.utils.StringUtils;

@Import(library={"context:js/editor/jquery.wysiwyg.js","context:js/pages.js"})
public class EditPages extends AdminBasePage{

	@Inject private Messages _messages;

	@Property @Persist
	private Page _page;

	private App _app;

	private Category _root;

	private Category _category;

	@OnEvent(value=EventConstants.ACTIVATE)
	public Object activate(Object... params){
		_app = (App)getSessionData().get("current_app");
		if(_app == null){
			addErrMsg(translate("error.select_app"), null);
			return Index.class;
		}
		if(params == null || params.length == 0){
			if(_page == null){
				addErrMsg(translate("error.select_page"), null);
				return Pages.class;
			}
		} else {
			if(params[0].toString().equals("new")){
				_page = new Page();
			} else {
				try {
					Session s = getTransactionManager().getSession();
					s.beginTransaction();
					_page = Page.getInstance().findById(s, Long.parseLong(params[0].toString()));
				} catch (Exception e) {
					getLog().error(e.getMessage(),e);
					addErrMsg(e.getMessage(), null);
					return Pages.class;
				}
			}
		}
		return null;
	}

	public String getTitle(){
		return _page.getId() == 0 ? translate("header.new_page") : translate("header.edit_page");
	}

	@OnEvent(component="form")
	public Object submit(){
		if(getRequest().getRequest().getParameter("cancel") != null){
			_page = null;
			return Pages.class;
		}
		try {
			if(StringUtils.isBlank(_page.getUrl()))
				addErrMsg(translate("error.name_required"), "url");
			if(StringUtils.isBlank(_page.getTitle()))
				addErrMsg(translate("error.title_required"), "title");
			if(!getAppMessages().empty())
				return null;

			String catId = getRequest().getRequest().getParameter("catid");
			if(catId != null){
				_page.setCategoryId(Long.parseLong(catId));
			}
			_page.setAppId(_app.getId());
			_page.setType(Page.PAGE_TYPE_STATIC);
			Session s = getTransactionManager().getSession();
			s.beginTransaction();
			_page.saveOrUpdate(s);
			s.flush();
			_page = null;
			addSucMsg(translate("message.page_saved"), null);
			return Pages.class;
		} catch (Exception e) {
			getTransactionManager().rollback();
			addErrMsg(e.getMessage(), null);
			getLog().error(e.getMessage(), e);
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

	public Category getCategory(){
		if(_category == null){
			if(_page.getCategoryId() != null){
				Session s = getTransactionManager().getSession();
				s.beginTransaction();
				try {
					_category = Category.getInstance().get(s, _page.getCategoryId());
				} catch (Exception e) {
					getLog().error(e.getMessage(),e);
				}
			}
			if(_category == null)
				_category = getRoot();
		}
		return _category;
	}

	public String getCategoryName(){
		return "root".equals(getCategory().getName()) ? _messages.get("label.no_category") : getCategory().getName();
	}
}
