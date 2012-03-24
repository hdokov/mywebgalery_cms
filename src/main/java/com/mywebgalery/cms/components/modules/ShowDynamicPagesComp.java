package com.mywebgalery.cms.components.modules;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.mywebgalery.cms.base.BaseComponent;
import com.mywebgalery.cms.model.Category;
import com.mywebgalery.cms.model.Page;

//@Import(library={"js/moduledynamic.js"})
public class ShowDynamicPagesComp extends BaseComponent {

	@Inject private Messages _messages;

	private List<Page> _pages;

	@Property
	private Page _page;

	@Property
	private Category _cat;

	@Parameter @Property
	private Category _category;

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

	public String getShowLink(){
		return String.format("<a href='?action=view&page=%s'>%s</a>", _page.getId(), _page.getTitle());
	}

	public String getEditLink(){
		return String.format("<a href='?action=edit&page=%s'>%s</a>", _page.getId(), _messages.get("label.edit"));
	}

}
