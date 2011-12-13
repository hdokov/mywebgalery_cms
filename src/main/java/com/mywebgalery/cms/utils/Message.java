package com.mywebgalery.cms.utils;

public class Message {

	public static final String ERR = "msgs:error";
	public static final String INF = "msgs:info";
	public static final String SUC = "msgs:succ";

	public static Message createError(String message, String comp){return new Message(message, ERR, comp);}
	public static Message createInfo(String message, String comp){return new Message(message, INF, comp);}
	public static Message createSucc(String message, String comp){return new Message(message, SUC, comp);}

	private String _message;
	private String _type;
	private String _component;

	public Message(){}

	public Message(String message, String type){
		this(message, type, null);
	}

	public Message(String message, String type, String comp){
		_message = message;
		_type = type;
		_component = comp;
	}

	public String getMessage() {
		return _message;
	}
	public void setMessage(String message) {
		_message = message;
	}
	public String getType() {
		return _type;
	}
	public void setType(String type) {
		_type = type;
	}
	public String getComponent() {
		return _component;
	}
	public void setComponent(String component) {
		_component = component;
	}


}
