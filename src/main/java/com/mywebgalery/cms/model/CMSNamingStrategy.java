package com.mywebgalery.cms.model;

import org.hibernate.cfg.DefaultNamingStrategy;

@SuppressWarnings("serial")
public class CMSNamingStrategy extends DefaultNamingStrategy{

	@Override
	public String propertyToColumnName(String propertyName) {
		//if(propertyName.startsWith("_"))
		//	propertyName = propertyName.substring(1);
		return propertyName.toLowerCase();
	}


}
