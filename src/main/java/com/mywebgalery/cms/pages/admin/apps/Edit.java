package com.mywebgalery.cms.pages.admin.apps;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.hibernate.Session;

import com.mywebgalery.cms.base.BasePage;
import com.mywebgalery.cms.model.App;
import com.mywebgalery.cms.utils.StringUtils;

public class Edit extends BasePage{

	@Property @Persist
	private App _app;

	@OnEvent(value=EventConstants.ACTIVATE)
	public Object activate(Object... params){
		if(params == null || params.length == 0){
			if(_app == null){
				addErrMsg(translate("error.select_app"), null);
				return Index.class;
			}
		} else {
			if(params[0].toString().equals("new")){
				_app = new App();
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
		}
		return null;
	}

	@OnEvent(component="form")
	public Object submit(){
		try {
			if(StringUtils.isBlank(_app.getName()))
				addErrMsg(translate("error.name_required"), "name");
			if(StringUtils.isBlank(_app.getTitle()))
				addErrMsg(translate("error.title_required"), "title");
			if(!getAppMessages().empty())
				return null;

			if(_app.getOwner() == 0){
				_app.setOwner(getUser().getId());
				_app.setAccountId(getUser().getAccountId());
			}

			Session s = getTransactionManager().getSession();
			s.beginTransaction();
			_app.saveOrUpdate(s);

			addSucMsg(translate("message.app_saved"), null);
			return Index.class;
		} catch (Exception e) {
			getTransactionManager().rollback();
			addErrMsg(e.getMessage(), null);
			getLog().error(e.getMessage(), e);
		}
		return null;
	}
}
