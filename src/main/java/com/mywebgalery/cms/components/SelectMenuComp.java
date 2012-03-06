package com.mywebgalery.cms.components;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.base.AbstractField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

@Import(library={"js/modulemenu.js"})
public class SelectMenuComp extends AbstractField {

	@Inject private Request _request;

	@SuppressWarnings("unused")
	@Parameter(required=true) @Property
	private String _id;

	@SuppressWarnings("unused")
	@Parameter @Property
	private String _name;

	@Override
	protected void processSubmission(String elementName) {
		_id = _request.getParameter(getControlName());
	}
}
