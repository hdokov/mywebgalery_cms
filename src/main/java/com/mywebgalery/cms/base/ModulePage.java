package com.mywebgalery.cms.base;

import org.apache.tapestry5.annotations.Environmental;

import com.mywebgalery.cms.model.Module;

public abstract class ModulePage extends BasePage{

	public abstract void initData();

	@Environmental
	private Module _module;

	public Module getModule() {
		if(getSessionData().get("init_module") != null){
			initData();
			getSessionData().put("init_module", null);
		}
		return _module;
	}

}
