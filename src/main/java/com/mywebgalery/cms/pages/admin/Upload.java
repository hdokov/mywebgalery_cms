package com.mywebgalery.cms.pages.admin;

import java.awt.image.BufferedImage;
import java.io.File;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.upload.services.UploadedFile;

import com.mywebgalery.cms.base.AdminBasePage;
import com.mywebgalery.cms.utils.ImageUtils;
import com.mywebgalery.cms.utils.StringUtils;

public class Upload extends AdminBasePage {

	@Property
    private UploadedFile _file;
	private String _error;
	@Property
	private String _type;
	@Property
	private String _size;
	@Property
	private String _id;

	@OnEvent(value=EventConstants.ACTIVATE)
	public void activate(String size, String type, String id){
		_type = type;
		_size = size;
		_id = id;
	}

	@OnEvent(component="form")
	public void submit(){
		try{
			if(_file != null && !StringUtils.isBlank(_file.getFileName())){
				File img = new File(ImageUtils.RESOURCES_PATH + _type+"/"+_size+"/" + _id +".png");
				img.mkdirs();
				BufferedImage image = ImageUtils.load(_file.getStream());
				ImageUtils.save(image, img);
			} else {
				_error = "Please select file to upload.";
			}
		} catch (Exception e) {
			getLog().error(e.getMessage(), e);
			_error = "Could not upload file: " + e.getMessage();
		}
	}

	public String getError() {
		return _error;
	}



}
