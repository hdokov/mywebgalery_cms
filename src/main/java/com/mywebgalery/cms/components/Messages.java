package com.mywebgalery.cms.components;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

import com.mywebgalery.cms.services.AppMessages;
import com.mywebgalery.cms.utils.Message;


public class Messages {

	private Map<String, String> _classes = new HashMap<String, String>();

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String _errorClass = "error_message";
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String _infoClass = "info_message";
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String _successClass = "success_message";

	@Parameter(defaultPrefix = BindingConstants.LITERAL) @Property
	private String _listClass = "messages_list";
	@Parameter(defaultPrefix = BindingConstants.LITERAL) @Property
	private String _component = null;

	@SessionState
	private AppMessages _messages;

	public Messages() {
		_classes.put(Message.ERR, _errorClass);
		_classes.put(Message.INF, _infoClass);
		_classes.put(Message.SUC, _successClass);
	}

	@BeginRender
	public void render(MarkupWriter writer){
		writer.element("ul", "class", _listClass);
		if(_component == null){
			for(Message m:_messages.getErrorMsgs()){
				writer.element("li", "class", getErrorClass());
				writer.write(m.getMessage());
				writer.end();
			}
			for(Message m:_messages.getInfoMsgs()){
				writer.element("li", "class", getInfoClass());
				writer.write(m.getMessage());
				writer.end();
			}
			for(Message m:_messages.getSuccMsgs()){
				writer.element("li", "class", getSuccessClass());
				writer.write(m.getMessage());
				writer.end();
			}
		} else {
			for(Message m:_messages.getCompMsgs(_component)){
				writer.element("li", "class", _classes.get(m.getType()));
				writer.write(m.getMessage());
				writer.end();
			}
		}
		writer.end();

	}

	public String getErrorClass() {
		return _classes.get(Message.ERR);
	}

	public void setErrorClass(String errorClass) {
		_classes.put(Message.ERR, errorClass);
	}

	public String getInfoClass() {
		return _classes.get(Message.INF);
	}

	public void setInfoClass(String infoClass) {
		_classes.put(Message.INF, infoClass);
	}

	public String getSuccessClass() {
		return _classes.get(Message.SUC);
	}

	public void setSuccessClass(String successClass) {
		_classes.put(Message.SUC, successClass);
	}

}
