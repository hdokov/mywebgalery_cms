package com.mywebgalery.cms.pages.admin.apps;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanBlockSource;
import org.apache.tapestry5.services.Environment;
import org.hibernate.Session;

import com.mywebgalery.cms.base.AdminBasePage;
import com.mywebgalery.cms.model.Module;

public class EditModule extends AdminBasePage{

	@Inject private BeanBlockSource _blockSource;

	@Property @Persist
	private Module _module;

    @Inject private Environment _environment;


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
		_environment.push(Module.class, _module);
		return null;
	}

	@CleanupRender
	public void clean(){
		_environment.pop(Module.class);
	}

	public String getTitle(){
		return _module.getId() == 0 ? translate("header.new_app") : translate("header.edit_app");
	}

	public Block getBlock(){
		Block b = _blockSource.getEditBlock(_module.getType());
		return b;
	}
}
