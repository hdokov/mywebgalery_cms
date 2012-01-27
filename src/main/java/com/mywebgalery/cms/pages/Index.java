package com.mywebgalery.cms.pages;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;

import org.apache.tapestry5.Asset2;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.ioc.Resource;
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

	public Asset2 getTemplate(){
		return new Asset2() {

			public String toClientURL() {
				return null;
			}

			public Resource getResource() {
				return new Resource() {

					private String template = "<div><div id='param:header'></div>test template <div id='param:left'></div></div>";

					public Resource withExtension(String extension) {
						return this;
					}

					public URL toURL() {
						return null;
					}

					public InputStream openStream() throws IOException {
						return new ByteArrayInputStream(template.getBytes());
					}

					public String getPath() {
						return null;
					}

					public String getFolder() {
						return null;
					}

					public String getFile() {
						return null;
					}

					public Resource forLocale(Locale locale) {
						return this;
					}

					public Resource forFile(String relativePath) {
						return this;
					}

					public boolean exists() {
						return true;
					}
				};
			}

			public boolean isInvariant() {
				return false;
			}
		};
		//return String.format("context:templates/%s/template.html", _theme);
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
		//_environment.pop(Module.class);
	}
}
