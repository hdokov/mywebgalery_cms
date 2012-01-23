package com.mywebgalery.cms.pages.modules;

import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.OnEvent;
import org.hibernate.Session;

import com.mywebgalery.cms.base.BasePage;
import com.mywebgalery.cms.model.Module;
import com.mywebgalery.cms.pages.admin.apps.Modules;

public class ModuleMenu extends BasePage {

	@Environmental
	private Module context;

	@OnEvent(component="form")
	public Object submit(){
		try {
			if(getRequest().getRequest().getParameter("cancel")!=null){
				getTransactionManager().rollback();
			} else {
				Session s = getTransactionManager().getSession();
				s.beginTransaction();
				context.saveOrUpdate(s);
			}
			return Modules.class;
		} catch (Exception e) {
			getLog().error(e.getMessage(),e);
			addErrMsg(e.getMessage(), null);
		}
		return null;
	}

	public Module getModule() {
		return context;
	}

}
