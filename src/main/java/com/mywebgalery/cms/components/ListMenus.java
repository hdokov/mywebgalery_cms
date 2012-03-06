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
import com.mywebgalery.cms.model.Menu;

@Import(library="js/menus.js")
public class ListMenus extends BaseComponent {

	@Inject private ComponentResources _res;
	@Inject private Messages _messages;

	@Parameter @Property
	private boolean _controls;

	@Parameter(required=true) @Property
	private Menu _menu;

	@OnEvent(value="del")
	public void delete(Long id, Long parent){
		try {
			Session s = getTransactionManager().getSession();
			s.beginTransaction();
			Menu.getInstance().deleteById(s, id);
			s.flush();
		} catch (Exception e) {
			getLog().error(e.getMessage(),e);
			addErrMsg(translate("error.cannot_delete_menu"), null);
		}
	}

	@BeginRender
	public void render(MarkupWriter w){
		if(_menu != null){
			if(_menu.getParent() == null){
				for(Menu c : _menu.getSubmenus())
					renderMenu(c,w);
			} else {
				renderMenu(_menu, w);
			}
		}
	}

	private void renderMenu(Menu c, MarkupWriter w){
		if(c == null)
			return;
		w.element("li", "class","menu_wrap");
		w.element("a",
				"class", _controls ? "editmenu" : "menu",
				"href", c.getId(),
				"page", c.getUri(),
				"parent", c.getParent().getId());
		w.write(c.getName());
		w.end();//a.Menu
		if(_controls){
			w.element("a",
					"class", "del_menu",
					"href", _res.createEventLink("del", c.getId(), c.getParent()));
			w.write(_messages.get("label.delete"));
			w.end();//a.del
			w.element("a",
					"class", "add_submenu",
					"href", c.getId());
			w.write(_messages.get("label.add_submenu"));
			w.end();//a.add_subMenu
		}
		if(c.getSubmenus().size()>0){
			w.element("ul", "class", "menu_sublist");
			for(Menu cc : c.getSubmenus())
				renderMenu(cc, w);
			w.end();//ul.Menu_sublist
		}
		w.end();//li.Menu_wrap
	}
}
