package com.mywebgalery.cms.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessagesMngr {

	private static final Logger _log = LoggerFactory.getLogger(MessagesMngr.class);
	private static final Map<String, Properties> _messages = new HashMap<String, Properties>();
	private static final Map<String, Map<String, List<String>>> _lists = new HashMap<String, Map<String,List<String>>>();

	public static void reset(){
		_messages.clear();
		_lists.clear();
	}

	protected static void load(String locale){
		try{
			String path = ConfigMngr.getDataDir()+"/messages";
			if(locale != null && !"default".equals(locale))
				path += "_"+locale;
			path += ".properties";
			Properties p = new Properties();
			FileInputStream in = new FileInputStream(new File(path));
			p.load(in);
			try {
				in.close();
			} catch (Exception e) {
				_log.error(e.getMessage(), e);
			}
			_messages.put(locale == null?"default":locale, p);
		}catch (Exception e) {
			_messages.put(locale == null?"default":locale, new Properties());
			_log.error("Cannot load properties.", e);
		}
	}

	protected static Properties getProperties(String locale){
		Properties p = _messages.get(locale == null?"default":locale);
		if(p == null){
			load(locale);
			p = _messages.get(locale == null?"default":locale);
		}
		return p;
	}

	protected static Map<String, List<String>> getMap(String locale){
		Map<String, List<String>> result = _lists.get(locale);
		if(result == null){
			result = new HashMap<String, List<String>>();
			_lists.put(locale, result);
		}
		return result;
	}

	protected static List<String> loadList(String key, String locale){
		Properties p = getProperties(locale);
		List<String> list= new ArrayList<String>();
		int i = 1;
		while(true) {
			String prop = p.getProperty(key+"."+i);
			if(prop != null){
				list.add(prop);
				i++;
			} else
				break;
		}
		return list;
	}

	public static String getMessage(String key){
		String val = getProperties("default").getProperty(key);
		if(val == null)
			val = "[["+key+"]]";
		return val;
	}

	public static String getMessage(String key, String locale){
		String val = getProperties(locale).getProperty(key);
		if(val == null)
			return getMessage(key);

		return val;
	}



	public static List<String> getList(String key){
		List<String> result = getMap("default").get(key);
		if(result == null){
			result = loadList(key, "default");
			getMap("default").put(key, result);
		}
		return result;
	}

	public static List<String> getList(String key, String locale){
		List<String> result = getMap(locale).get(key);
		if(result == null) {
			result = loadList(key, locale);
			getMap(locale).put(key, result);
		}
		if(result.isEmpty())
			return getList(key);
		return result;
	}

	public static void main(String[] args) {
		System.out.println(getMessage("test.msg"));
		for(String s : getList("test.list"))
			System.out.println(s);
	}
}
