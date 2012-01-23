package com.mywebgalery.cms.pages.admin.apps;

import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.hibernate.Session;

import com.mywebgalery.cms.base.AdminBasePage;
import com.mywebgalery.cms.model.App;
import com.mywebgalery.cms.model.Module;
import com.mywebgalery.cms.services.ModuleDescriptor;
import com.mywebgalery.cms.services.ModulesSourceService;

public class NewModule extends AdminBasePage {

	private List<ModuleDescriptor> _modules;

	@Inject private ModulesSourceService _modulesSource;
	@Inject private Messages _messages;
	@Inject private PageRenderLinkSource _linkSource;

	@Property
	private ModuleDescriptor _module;

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

	@OnEvent(component="select")
	public Object select(String moduleName){
		Module m = new Module();
		m.setAppId(_app.getId());
		m.setType(moduleName);
		try {
			Session s = getTransactionManager().getSession();
			s.beginTransaction();
			m.save(s);
			s.flush();
			return _linkSource.createPageRenderLinkWithContext(EditModule.class, m.getId());
		} catch (Exception e) {
			addErrMsg(e.getMessage(), null);
			getLog().error(e.getMessage(), e);
		}
		return null;
	}

	public List<ModuleDescriptor> getModules() {
		if(_modules == null){
			_modules = _modulesSource.getAllModules();
		}
		return _modules;
	}

	public String getName(){
		String name = _messages.get(_module.getNameKey()+".name");
		return name;
	}

	public String getDescr(){
		String name = _messages.get(_module.getNameKey()+".descr");
		return name;
	}

}
