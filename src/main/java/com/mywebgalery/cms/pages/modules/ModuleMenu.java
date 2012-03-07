package com.mywebgalery.cms.pages.modules;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.mywebgalery.cms.base.BasePage;
import com.mywebgalery.cms.model.Menu;
import com.mywebgalery.cms.model.Module;
import com.mywebgalery.cms.pages.admin.apps.Modules;

public class ModuleMenu extends BasePage {

	public static final String MENU_ID_KEY = "menuId";
	public static final String MENU_SHOW_ROOT_KEY = "showRoot";
	public static final String MENU_SHOW_SUB_KEY = "showSubmenus";
	public static final String MENU_SHOW_HEADER_KEY = "showMenuHeader";
	//public static final String MENU_NAME_KEY = "menuName";

	@Environmental
	private Module _module;

	private Map<String, String> data;

	private Menu _root;

	private Menu _menu;

	@Property
	private Menu _current;

	@OnEvent(component="form")
	public Object submit(){
		try {
			if(getRequest().getRequest().getParameter("cancel")!=null){
				getTransactionManager().rollback();
			} else {
				_module.setData(JSONValue.toJSONString(getData()));
				Session s = getTransactionManager().getSession();
				s.beginTransaction();
				_module.saveOrUpdate(s);
			}
			return Modules.class;
		} catch (Exception e) {
			getLog().error(e.getMessage(),e);
			addErrMsg(e.getMessage(), null);
		}
		return null;
	}

	public Menu getRoot() {
		if(_root == null){
			try {
				Session s = getTransactionManager().getSession();
				s.beginTransaction();
				_root = Menu.getInstance().getRootMenu(s, _module.getAppId());
			} catch (Exception e) {
				getLog().error(e.getMessage(),e);
				addErrMsg(e.getMessage(), null);
			}
			if(_root == null)
				_root = new Menu();
		}
		return _root;
	}

	private Map<String, String> getData(){
		if(data == null){
			data = new HashMap<String, String>();
			if(_module.getData() != null){
				JSONObject o = (JSONObject)JSONValue.parse(_module.getData());
				for(Object k : o.keySet()){
					data.put(String.valueOf(k), String.valueOf(o.get(k)));
				}
			}
		}
		return data;
	}

	public Module getModule() {
		return _module;
	}

	public String getMenuId(){
		return getData().get(MENU_ID_KEY);
	}

	public void setMenuId(String id){
		getData().put(MENU_ID_KEY, id);
	}

	public String getMenuName(){
		return getMenu() == null ? "" : getMenu().getName();
	}

	public boolean getShowRoot(){
		return "true".equals(getData().get(MENU_SHOW_ROOT_KEY));
	}

	public void setShowRoot(boolean show){
		getData().put(MENU_SHOW_ROOT_KEY, String.valueOf(show));
	}

	public boolean getShowSubmenus(){
		return "true".equals(getData().get(MENU_SHOW_SUB_KEY));
	}

	public void setShowSubmenus(boolean show){
		getData().put(MENU_SHOW_SUB_KEY, String.valueOf(show));
	}

	public boolean getShowHeader(){
		return "true".equals(getData().get(MENU_SHOW_HEADER_KEY));
	}

	public void setShowHeader(boolean show){
		getData().put(MENU_SHOW_HEADER_KEY, String.valueOf(show));
	}

	public Menu getMenu(){
		if(_menu == null){
			if(getMenuId() != null){
				try {
					Session s = getTransactionManager().getSession();
					s.beginTransaction();
					_menu = Menu.getInstance().get(s, Long.parseLong(getMenuId()));
				} catch (Exception e) {
					addErrMsg(translate("error.cannot_find_menu"), null);
					getLog().error(e.getMessage(), e);
				}
			}
		}
		return _menu;
	}
}
