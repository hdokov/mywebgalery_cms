package com.mywebgalery.cms.components.modules;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.hibernate.Session;

import com.mywebgalery.cms.base.BaseComponent;
import com.mywebgalery.cms.model.Category;
import com.mywebgalery.cms.model.Page;

//@Import(library={"js/moduledynamic.js"})
public class ViewDynamicPagesComp extends BaseComponent {

	@Inject private Request _request;

	private Page _page;

	@SuppressWarnings("unused")
	@Parameter @Property
	private Category _category;

	public Page getPage() {
		if(_page == null){
			String pageid = _request.getParameter("page");
			if(pageid != null){
				try {
					Session s = getTransactionManager().getSession();
					s.beginTransaction();
					_page = Page.getInstance().get(s, Long.parseLong(pageid));
				} catch (Exception e) {
					addErrMsg(translate("error.cannot_get_page"), null);
					getLog().error(e.getMessage(), e);
				}
			}
			if(_page == null)
				_page = new Page();
		}
		return _page;
	}




}
