package com.mywebgalery.cms.pages.admin.apps;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.hibernate.Session;

import com.mywebgalery.cms.base.AdminBasePage;
import com.mywebgalery.cms.model.App;
import com.mywebgalery.cms.model.Page;
import com.mywebgalery.cms.utils.StringUtils;

public class EditPages extends AdminBasePage{

	@Property @Persist
	private Page _page;

	private App _app;


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

			_page.setAppId(_app.getId());

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
}
