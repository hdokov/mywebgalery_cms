package com.mywebgalery.cms.pages;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanBlockSource;
import org.apache.tapestry5.services.Environment;

import com.mywebgalery.cms.base.BasePage;
import com.mywebgalery.cms.model.Module;


/**
 * Start page of application cms.
 */
public class Index extends BasePage {

	@Inject private BeanBlockSource _blockSource;

	private String _theme = "base";
	private String _test;
    @Inject
    private Environment _environment;


	public String getTitle(){
		return "title string";
	}

	public String getTemplate(){
		return String.format("context:templates/%s/template.html", _theme);
	}
	public String getTemplateCss(){
		return String.format("/templates/%s/template.css", _theme);
	}
	public String getTemplateJs(){
		return String.format("/templates/%s/template.js", _theme);
	}

	public Block getBlock(){
		return _blockSource.getDisplayBlock("module.login");
	}

	public String getDynamicCss(){
		return "body {background:#eee;}";
	}

	public String[] getTests(){
		return new String[]{"ts1t", "module.login", "t2st"};
	}

	public String getTest() {
		return _test;
	}

	public void setTest(String test) {
		Module m = new Module();
		m.setData(test);
		m.setType(test);
		_environment.push(Module.class, m);
		_test = test;
	}

	@CleanupRender
	public void clean(){
		_environment.pop(Module.class);
	}
}
