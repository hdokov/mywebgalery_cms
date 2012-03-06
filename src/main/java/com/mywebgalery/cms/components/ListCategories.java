package com.mywebgalery.cms.components;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.mywebgalery.cms.base.BaseComponent;
import com.mywebgalery.cms.model.Category;

@Import(library="js/categories.js")
public class ListCategories extends BaseComponent {

	@Inject private ComponentResources _res;
	@Inject private Messages _messages;

	@Parameter @Property
	private boolean _controls;

	@Parameter(required=true) @Property
	private Category _category;

	@OnEvent(value="del")
	public void delete(Long id, Long parent){
		try {
			Session s = getTransactionManager().getSession();
			s.beginTransaction();
			Category.getInstance().deleteById(s, id);
			s.flush();
		} catch (Exception e) {
			getLog().error(e.getMessage(),e);
			addErrMsg(translate("error.cannot_delete_category"), null);
		}
	}

	@BeginRender
	public void render(MarkupWriter w){
		if(_category != null){
			if(_category.getParentCategory() == null){
				if(!_controls)
					renderCategory(_category, w, false);
				for(Category c : _category.getSubcategories())
					renderCategory(c,w, true);
			} else {
				renderCategory(_category, w, true);
			}
		}
	}

	private void renderCategory(Category c, MarkupWriter w, boolean renderChildren){
		if(c == null)
			return;
		w.element("li", "class","category_wrap");
		w.element("a",
				"class", _controls ? "editcategory" : "category",
				"href", c.getId(),
				"parent", c.getParentCategory());
		w.write("root".equals(c.getName()) ? _messages.get("label.no_category") : c.getName());
		w.end();//a.category
		if(_controls){
			w.element("a",
					"class", "del_category",
					"href", _res.createEventLink("del", c.getId(), c.getParentCategory()));
			w.write(_messages.get("label.delete"));
			w.end();//a.del
			w.element("a",
					"class", "add_subcategory",
					"href", c.getId());
			w.write(_messages.get("label.add_subcategory"));
			w.end();//a.add_subcategory
		}
		if(renderChildren && c.getSubcategories().size()>0){
			w.element("ul", "class", "category_sublist");
			for(Category cc : c.getSubcategories())
				renderCategory(cc, w, true);
			w.end();//ul.category_sublist
		}
		w.end();//li.category_wrap
	}
}
