package com.mywebgalery.cms.pages;

import java.net.URL;

import org.apache.tapestry5.Asset2;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.RequestGlobals;
import org.hibernate.Session;

import com.mywebgalery.cms.base.BasePage;
import com.mywebgalery.cms.model.App;
import com.mywebgalery.cms.model.Page;


/**
 * Start page of application cms.
 */
public class Index extends BasePage {

    @Inject private Environment _environment;
    @Inject private RequestGlobals _request;

    private Page _page;
    private App _app;
    private String _appName;

	@OnEvent(value=EventConstants.ACTIVATE)
	public Object activate(Object... params){
		_app = getApp();
		try{
			Session s = getTransactionManager().getSession();
			s.beginTransaction();
			if(params != null && params.length > 0){
				_page = Page.getInstance().getByName(s, _app.getId(), params[0].toString());
			} else {
				_page = Page.getInstance().getDefault(s, _app.getId());
			}
		} catch (Exception e) {
			getLog().error(e.getMessage(), e);
			addErrMsg(e.getMessage(), null);
		}
		return null;
	}


	public String getTitle(){
		return _page.getTitle();
	}

	public Asset2 getTemplate(){
		return new Asset2() {

			public String toClientURL() {
				return null;
			}

			public Resource getResource() {
				return getPage();
			}

			public boolean isInvariant() {
				return false;
			}
		};
		//return String.format("context:templates/%s/template.html", _theme);
	}

	public Asset2 getWrap(){
		return new Asset2() {

			public String toClientURL() {
				return null;
			}

			public Resource getResource() {
				return getApp();
			}

			public boolean isInvariant() {
				return false;
			}
		};
		//return String.format("context:templates/%s/template.html", _theme);
	}

	public String getTemplateCss(){
		return String.format("/templates/%s/template.css", getApp().getTemplate());
	}
	public String getThemeCss(){
		return String.format("/themes/%s/theme.css", getApp().getTheme());
	}
	public String getTemplateJs(){
		return String.format("/templates/%s/template.js", getApp().getTemplate());
	}

	public String getDynamicCss(){
		return getApp().getCss();
	}

	public String getDynamicJs(){
		return getApp().getJs();
	}

	public Page getPage(){
		return _page;
	}

	@SetupRender
	public void setup(){
		_environment.push(Page.class, getPage());
	}

	@CleanupRender
	public void clean(){
		_environment.pop(Page.class);
	}

	public String getAppName(){
		if(_appName == null){
			try{
				URL url = new URL(_request.getHTTPServletRequest().getRequestURL().toString());
				String host = url.getHost();
				if(host.startsWith("www."))
					host = host.substring(4);
				int dot = host.indexOf('.');
				if(dot > -1)
					_appName = host.substring(0,dot);
				else
					_appName = host;
			} catch (Exception e) {
				getLog().error(e.getMessage(), e);
				_appName = "mywebgalery";
			}
		}
		return _appName;
	}

	public App getApp(){
		if(_app == null){
			Session s = getTransactionManager().getSession();
			s.beginTransaction();
			try {
				_app = App.getInstance().get(s, getAppName());
				if(_app == null)
					_app = App.getInstance().get(s, 0L);
			} catch (Exception e) {
				getLog().error(e.getMessage(), e);
				_app = new App();
				_app.setName("unknown");
				_app.setTitle("Unknown");
				_app.setDescr("My Web Galery. Unknown app.");
				_app.setKeywords(translate("default_keywords"));
			}
		}
		return _app;
	}


}
