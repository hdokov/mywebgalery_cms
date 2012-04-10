package com.mywebgalery.cms.pages.modules;

import org.apache.tapestry5.annotations.OnEvent;
import org.hibernate.Session;

import com.mywebgalery.cms.base.ModulePage;
import com.mywebgalery.cms.model.Module;
import com.mywebgalery.cms.pages.admin.apps.Modules;

public class ModuleHtml extends ModulePage {

	@Override
	public void initData() {
		//do nothing
	}

	@OnEvent(component="htmlform")
	public Object submit(){
		try {
			if(getRequest().getRequest().getParameter("cancel")!=null){
				getTransactionManager().rollback();
			} else {
				Session s = getTransactionManager().getSession();
				s.beginTransaction();
				getModule().saveOrUpdate(s);
			}
			return Modules.class;
		} catch (Exception e) {
			getLog().error(e.getMessage(),e);
			addErrMsg(e.getMessage(), null);
		}
		return null;
	}

	public String getHtml(){
		return getModule().getContent();
	}

	public Module getModule() {
		return getModule();
	}


}
