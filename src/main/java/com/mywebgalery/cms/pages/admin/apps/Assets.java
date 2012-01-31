package com.mywebgalery.cms.pages.admin.apps;

import java.io.File;
import java.io.FileFilter;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import com.mywebgalery.cms.base.AdminBasePage;
import com.mywebgalery.cms.model.App;
import com.mywebgalery.cms.utils.ConfigMngr;

public class Assets extends AdminBasePage {

	public static final FileFilter DIRS = new FileFilter() {

		public boolean accept(File pathname) {
			return pathname.isDirectory();
		}
	};

	public static final FileFilter FILES = new FileFilter() {

		public boolean accept(File pathname) {
			return pathname.isFile();
		}
	};

	private App _app;

	private File _currentRoot;

	@Property
	private File _file;

	@OnEvent(value=EventConstants.ACTIVATE)
	public Object activate(Object... params){
		_app = (App)getSessionData().get("current_app");
		if(_app == null){
			addErrMsg(translate("error.select_app"), null);
			return Index.class;
		}
		if(_currentRoot == null){
			try {
				_currentRoot = new File(ConfigMngr.getUploadDir(),_app.getName()+"/");
				if(!_currentRoot.exists()){
					_currentRoot.mkdirs();
				}
			} catch (Exception e) {
				addErrMsg(e.getMessage(), null);
				getLog().error(e.getMessage(),e);
			}
		}
		return null;
	}

	public App getApp() {
		return _app;
	}

	public File[] getDirs(){
		return _currentRoot.listFiles(DIRS);
	}

	public File[] getFiles(){
		return _currentRoot.listFiles(FILES);
	}

}
