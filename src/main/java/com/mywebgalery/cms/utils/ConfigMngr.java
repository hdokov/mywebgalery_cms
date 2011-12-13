package com.mywebgalery.cms.utils;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigMngr {

	private static final Logger _log = LoggerFactory.getLogger(ConfigMngr.class);
	private static final Properties _properties = new Properties();
	static{
		try{
			_properties.load(ConfigMngr.class.getResourceAsStream("/conf/product.properties"));
		}catch (Exception e) {
			_log.error("Cannot load properties.", e);
		}
	}

	public static String getProperty(String prop){
		return _properties.getProperty(prop);
	}

	public static String getAdminAppName(){
		return getProperty("admin_app_name");
	}

	public static String getDataDir(){
		return getProperty("data_dir");
	}

	public static String getUploadDir(){
		return getProperty("upload_dir");
	}

	public static String getThemeDir(){
		return getProperty("theme_dir");
	}

}
