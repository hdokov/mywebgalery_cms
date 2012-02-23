package com.mywebgalery.cms.components;

import java.util.List;

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

@Import(library="context:js/categories.js")
public class ListCategories extends BaseComponent {

	@Inject private ComponentResources _res;
	@Inject private Messages _messages;

	@Parameter @Property
	private boolean _controls;

	@Parameter(required=true) @Property
	private List<Category> _categories;

	@OnEvent(value="del")
	public void delete(Long id){
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
		if(_categories != null){
			for(Category c : _categories)
				renderCategory(c,w);
		}
//		<li t:type='loop' t:source='categories' t:value='category' class='category'>
//		<div class="category_name" id="${category.id}" parent="${category.parentCategory}">${category.name}</div>
//		<div class="controls" t:type='if' test='controls'>
//			<t:actionlink t:id='del' t:context="category.id"  onclick="return confirm('${message:message.confirm_category_delete}');">${message:label.delete}</t:actionlink>
//		</div>
//		<t:if test="category.subcategories">
//			<ul class='category_sublist'>
//				<t:listcategories categories="category.subcategories" controls='prop:controls' />
//			</ul>
//		</t:if>
//		<a class="new" t:type='if' test='controls' href="${category.id}">#{message:label.add_subcategory}</a>
//	</li>

	}

	private void renderCategory(Category c, MarkupWriter w){
		w.element("li", "class","category_wrap");
		w.element("a",
				"class", "category",
				"href", c.getId(),
				"parent", c.getParentCategory());
		w.write(c.getName());
		w.end();//a.category
		if(_controls){
			w.element("a",
					"class", "del_category",
					"href", _res.createEventLink("del", c.getId()));
			w.write(_messages.get("label.delete"));
			w.end();//a.category
		}

		w.end();//li.category_wrap
	}
}
