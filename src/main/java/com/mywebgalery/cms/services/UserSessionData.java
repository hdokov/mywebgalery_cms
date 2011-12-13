package com.mywebgalery.cms.services;

import java.util.HashMap;
import java.util.Map;

import com.mywebgalery.cms.model.User;


public class UserSessionData {

	private Map<String, Object> _data = new HashMap<String, Object>();

	public Object get(String key){
		return _data.get(key);
	}

	public void put(String key, Object obj){
		_data.put(key, obj);
	}

	public User getUser(){
		return (User)get("user");
	}

	public void setUser(User admin){
		put("user", admin);
	}

	public void clear(){
		_data.clear();
	}
}
