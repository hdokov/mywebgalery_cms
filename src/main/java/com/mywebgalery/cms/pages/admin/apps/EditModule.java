package com.mywebgalery.cms.pages.admin.apps;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.hibernate.Session;

import com.mywebgalery.cms.base.AdminBasePage;
import com.mywebgalery.cms.model.Module;

public class EditModule extends AdminBasePage{

	@Property @Persist
	private Module _module;

	@OnEvent(value=EventConstants.ACTIVATE)
	public Object activate(Object... params){
		if(params != null && params.length > 0){
			try {
				Session s = getTransactionManager().getSession();
				s.beginTransaction();
				_module = Module.getInstance().findById(s, Long.parseLong(params[0].toString()));
			} catch (Exception e) {
				getLog().error(e.getMessage(),e);
				addErrMsg(e.getMessage(), null);
				return Modules.class;
			}
		}
		if(_module == null){
			addErrMsg(translate("error.select_module"), null);
			return Modules.class;
		}
		return null;
	}

	public String getTitle(){
		return _module.getId() == 0 ? translate("header.new_app") : translate("header.edit_app");
	}

}
