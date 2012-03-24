package com.mywebgalery.cms.components.modules;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Request;
import org.hibernate.Session;

import com.mywebgalery.cms.base.BaseComponent;
import com.mywebgalery.cms.model.App;
import com.mywebgalery.cms.model.Category;
import com.mywebgalery.cms.model.Page;
import com.mywebgalery.cms.pages.Index;

//@Import(library={"js/moduledynamic.js"})
public class EditDynamicPagesComp extends BaseComponent {

	@Inject private Request _request;
	@Inject private Messages _messages;
    @Inject private Environment _environment;

    @Inject private PageRenderLinkSource _linkSource;

    private Page _currentPage;

	private Page _page;

	@Parameter @Property
	private Category _category;

	private App _app;

	public Page getPage() {
		if(_page == null){
			String pageid = _request.getParameter("page");
			if(pageid != null && !"0".equals(pageid)){
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

	public App getApp() {
		if(_app == null){
			_app = _environment.peek(App.class);
			if(_app == null)
				_app = new App();
		}
		return _app;
	}

	public Page getCurrentPage() {
		if(_currentPage == null)
			_currentPage = _environment.peekRequired(Page.class);
		return _currentPage;
	}

	public String getHeader(){
		return getPage().getId()==0 ? _messages.get("label.new_article") : _messages.get("label.edit_article");
	}

	@OnEvent(component="form")
	public Object submit(){
		try {
			_page.setAppId(Long.parseLong(_request.getParameter("appid")));
			_page.setCategoryId(Long.parseLong(_request.getParameter("catid")));
			_page.setType(Page.PAGE_TYPE_ARTICLE);
			Session s = getTransactionManager().getSession();
			s.beginTransaction();
			_page.saveOrUpdate(s);
			s.flush();
		} catch (Exception e) {
			addErrMsg(_messages.get("error.cannot_save_page"), null);
			getLog().error(e.getMessage(), e);
		}
		return _linkSource.createPageRenderLinkWithContext(Index.class, _request.getParameter("currentpage"));
	}

}
