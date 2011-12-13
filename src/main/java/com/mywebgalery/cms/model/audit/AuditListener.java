package com.mywebgalery.cms.model.audit;

import org.hibernate.envers.RevisionListener;

public class AuditListener implements RevisionListener{

	public void newRevision(Object arg0) {
		Revision r = (Revision)arg0;

		AuditHelper helper = AuditHelper.LOCAL_INSTANCE.get();
		if(helper != null){
			r.setPageName(helper.getPageName());
			r.setUsername(helper.getUsername());
			r.setRequest(helper.getRequest());
		}
	}

}
