package com.mywebgalery.cms.components.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.hibernate.Session;

import com.mywebgalery.cms.base.BaseComponent;
import com.mywebgalery.cms.model.Category;
import com.mywebgalery.cms.model.Module;
import com.mywebgalery.cms.model.Page;

//@Import(library={"js/moduledynamic.js"})
public class ShowDynamicPagesComp extends BaseComponent {

	@Inject private Request _request;

	private List<Page> _pages;

	@Property
	private Page _page;

	@Property
	private Category _current;

	@Parameter(required=true) @Property
	private Module _module;

	@Parameter @Property
	private Category _category;

	@Parameter @Property
	private Map<String, String> _data;

	public List<Page> getPages() {
		if(_pages == null){
			try {
				Session s = getTransactionManager().getSession();
				s.beginTransaction();
				_pages = Page.getInstance().getByCategoryAndType(s, _category.getId(), Page.PAGE_TYPE_ARTICLE);
			} catch (Exception e) {
				addErrMsg("Cannot load pages.", null);
				getLog().error(e.getMessage(),e);
				_pages = new ArrayList<Page>();
			}
		}
		return _pages;
	}


}
