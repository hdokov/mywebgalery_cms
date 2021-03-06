package com.mywebgalery.cms.pages.admin.apps;

import java.io.File;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.hibernate.Session;

import com.mywebgalery.cms.base.AdminBasePage;
import com.mywebgalery.cms.model.App;
import com.mywebgalery.cms.utils.StringUtils;

@Import(library={"js/wrap.js"})
public class EditWrap extends AdminBasePage{

	@Inject
	private ApplicationGlobals _globals;

	@Property
	private App _app;

	private File _context;

	@Property
	private File _file;


	@OnEvent(value=EventConstants.ACTIVATE)
	public Object activate(Object... params){
		_app = (App)getSessionData().get("current_app");
		_context = new File(_globals.getServletContext().getRealPath("/"));
		if(_app == null){
			addErrMsg(translate("error.select_app"), null);
			return Index.class;
		}
		return null;
	}

	@OnEvent(component="form")
	public Object submit(){
		if(getRequest().getRequest().getParameter("cancel") != null){
			getTransactionManager().rollback();
			return Design.class;
		}
		try {
			if(StringUtils.isBlank(_app.getWrap()))
				addErrMsg(translate("error.content_required"), "wrap");
			if(!getAppMessages().empty())
				return null;

			Session s = getTransactionManager().getSession();
			s.beginTransaction();
			_app.update(s);
			s.flush();
			addSucMsg(translate("message.app_saved"), null);
			return Design.class;
		} catch (Exception e) {
			getTransactionManager().rollback();
			addErrMsg(e.getMessage(), null);
			getLog().error(e.getMessage(), e);
		}
		return null;
	}

	public File[] getTemplates(){
		return new File(_context, "templates").listFiles(Assets.DIRS);
	}

	public File[] getThemes(){
		return new File(_context, "themes").listFiles(Assets.DIRS);
	}

	public String getFileName(){
		return TapestryInternalUtils.toUserPresentable(_file.getName());
	}
}
