package com.mywebgalery.cms.pages.admin.apps;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.upload.services.UploadedFile;

import com.mywebgalery.cms.base.AdminBasePage;
import com.mywebgalery.cms.model.App;
import com.mywebgalery.cms.utils.ConfigMngr;
import com.mywebgalery.cms.utils.ImageUtils;
import com.mywebgalery.cms.utils.StringUtils;

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

	@Inject private PageRenderLinkSource _linkSource;

	private App _app;

	private File _currentRoot;

	@Property
    private UploadedFile _upload;
	@Property
	private File _file;

	@Property
	private int _index;
	@Property
	private String[] _path;
	private String _offset;

	@Property
	private String _newFolder;
	private String _dirOffset;

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
					if(!_currentRoot.mkdirs())
						addErrMsg(translate("error.cannot_create_folder"), null);
				}
				_offset = "";
				if(params != null && params.length > 0){
					_offset = params[0].toString() + "/";
					_currentRoot = new File(_currentRoot,_offset);
				}
				if(_offset.length() > 0){
					_path = _offset.split("/");
				} else {
					_path = new String[]{};
				}
				System.out.println(_path);
			} catch (Exception e) {
				addErrMsg(e.getMessage(), null);
				getLog().error(e.getMessage(),e);
			}
		}
		return null;
	}

	@OnEvent(component="addfolder")
	public Object addFolder(){
		if(StringUtils.isBlank(_newFolder)){
			addErrMsg(translate("error.name_required"), "foldername");
			return _linkSource.createPageRenderLinkWithContext(getClass(), _dirOffset);
		}
		_currentRoot = new File(_currentRoot,_dirOffset);
		File dir = new File(_currentRoot,_newFolder);
		if(dir.mkdir()){
			addSucMsg(translate("message.folder_added"), null);
		} else {
			addErrMsg(translate("error.cannot_create_folder"), null);
		}
		return _linkSource.createPageRenderLinkWithContext(getClass(), _dirOffset);
	}

	@OnEvent(component="addfile")
	public Object addFile(){
		try{
			if(_upload != null && !StringUtils.isBlank(_upload.getFileName())){
				_currentRoot = new File(_currentRoot,_dirOffset);
				File img = new File(_currentRoot,_upload.getFileName());
				BufferedImage image = ImageUtils.load(_upload.getStream());
				ImageUtils.save(image, img);
			} else {
				addErrMsg(translate("error.file_required"), null);
			}
		} catch (Exception e) {
			getLog().error(e.getMessage(), e);
			addErrMsg(translate("error.file_upload_error",e.getMessage()), null);
		}
		return _linkSource.createPageRenderLinkWithContext(getClass(), _dirOffset);
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

	public String getDirOffset(){
		return _offset + _file.getName();
	}

	public String getOffset(){
		return _offset;
	}
	public void setOffset(String dir){
		_dirOffset = dir;
	}

	public String getPathOffset(){
		StringBuffer offset = new StringBuffer();
		for(int i=0;i<=_index; i++){
			offset.append(_path[i]);
			if(i<_index)
				offset.append("/");
		}
		return offset.toString();
	}
	public String getPathName(){
		return _path[_index];
	}
	public boolean isLast(){
		return _index == _path.length -1;
	}

}
