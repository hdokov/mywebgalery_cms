package com.mywebgalery.cms.pages.modules;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.mywebgalery.cms.base.ModulePage;
import com.mywebgalery.cms.model.Category;
import com.mywebgalery.cms.pages.admin.apps.Modules;

public class ModuleDynamic extends ModulePage {

	public static final String CATEGORY_ID_KEY = "categoryId";
	public static final String CATEGORY_SHOW_SUB_KEY = "showSubcategories";

	private Map<String, String> _data;

	private Category _root;

	private Category _category;

	@SuppressWarnings("unused")
	@Property
	private Category _current;

	@Override
	public void initData() {
		_category = null;
		_data = null;
	}

	@OnEvent(component="form")
	public Object submit(){
		try {
			if(getRequest().getRequest().getParameter("cancel")!=null){
				getTransactionManager().rollback();
			} else {
				getModule().setData(JSONValue.toJSONString(getData()));
				Session s = getTransactionManager().getSession();
				s.beginTransaction();
				getModule().saveOrUpdate(s);
			}
			return Modules.class;
		} catch (Exception e) {
			getLog().error(e.getMessage(),e);
			addErrMsg(e.getMessage(), null);
		}
		return null;
	}

	public Category getRoot() {
		if(_root == null){
			try {
				Session s = getTransactionManager().getSession();
				s.beginTransaction();
				_root = Category.getInstance().getRootCategory(s, getModule().getAppId());
			} catch (Exception e) {
				getLog().error(e.getMessage(),e);
				addErrMsg(e.getMessage(), null);
			}
			if(_root == null)
				_root = new Category();
		}
		return _root;
	}

	public Map<String, String> getData(){
		if(_data == null){
			if(getModule().getData() != null){
				_data = new HashMap<String, String>();
				JSONObject o = (JSONObject)JSONValue.parse(getModule().getData());
				for(Object k : o.keySet()){
					_data.put(String.valueOf(k), String.valueOf(o.get(k)));
				}
			} else {
				//duplicated because getModule() may clear _data
				_data = new HashMap<String, String>();
			}
		}
		return _data;
	}

	public String getCategoryId(){
		return getData().get(CATEGORY_ID_KEY);
	}

	public void setCategoryId(String id){
		getData().put(CATEGORY_ID_KEY, id);
	}

	public String getCategoryName(){
		return getCategory() == null ? "" : getCategory().getName();
	}

	public boolean getShowSubcategories(){
		return "true".equals(getData().get(CATEGORY_SHOW_SUB_KEY));
	}

	public void setShowSubcategories(boolean show){
		getData().put(CATEGORY_SHOW_SUB_KEY, String.valueOf(show));
	}

	public Category getCategory(){
		if(_category == null){
			if(getCategoryId() != null){
				try {
					Session s = getTransactionManager().getSession();
					s.beginTransaction();
					_category = Category.getInstance().get(s, Long.parseLong(getCategoryId()));
				} catch (Exception e) {
					addErrMsg(translate("error.cannot_find_category"), null);
					getLog().error(e.getMessage(), e);
				}
			}
		}
		return _category;
	}

	public boolean show(String param){
		String action = getRequest().getRequest().getParameter("action");
		if(param.equals(action))
			return true;

		return "list".equals(param) && action == null;
	}
}
