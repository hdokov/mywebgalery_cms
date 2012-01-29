package com.mywebgalery.cms.components;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;

import com.mywebgalery.cms.base.BaseComponent;

/**
 * Layout component for pages of application shop.
 */
//@IncludeStylesheet({"context:css/base/jquery-ui.css","context:css/main.css","literal:/customcss"})
public class Layout extends BaseComponent {
	/** The page title, for the <title> element and the <h1>element. */

	public static final String[] PAGES = { "home", "about", "contact", "legal" };

	@Inject
	private RequestGlobals _request;

	@Inject
	private Messages _messages;

	@Property
	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String _title;

	@Property
	private String _page;

	@SuppressWarnings("unused")
	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private Block _sidebar;

	private String _logo;

	@OnEvent(component = "logout")
	public Object logout() {
		_request.getRequest().getSession(true).invalidate();
		return "admin/index";
	}

	public String getPageName() {
		return _messages.get("page."+_page+".name");
	}

	public String[] getPages() {
		return PAGES;
	}

	public int getYear(){
		return GregorianCalendar.getInstance().get(Calendar.YEAR);
	}

	public String getUserName(){
		return getSessionData().getUser() == null ? _messages.get("application.guest") : getSessionData().getUser().getName();
	}
}
