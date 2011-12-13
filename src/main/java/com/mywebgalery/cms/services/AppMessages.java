package com.mywebgalery.cms.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mywebgalery.cms.utils.Message;
import com.mywebgalery.cms.utils.StringUtils;


public class AppMessages {

	private Map<String, List<Message>> _messages = new HashMap<String, List<Message>>();

	public List<Message> getErrorMsgs(){
		return getList(Message.ERR);
	}

	public List<Message> getInfoMsgs(){
		return getList(Message.INF);
	}

	public List<Message> getSuccMsgs(){
		return getList(Message.SUC);
	}

	public List<Message> getCompMsgs(String id){
		return getList(id);
	}


	public void addMsg(Message m){
		if(StringUtils.isEmpty(m.getComponent())){
			getList(m.getType()).add(m);
		} else {
			getList(m.getComponent()).add(m);
		}
	}

	private List<Message> getList(String key){
		List<Message> list = _messages.get(key);
		if(list == null){
			list = new ArrayList<Message>();
			_messages.put(key, list);
		}
		return list;
	}

	public void addErrMsg(String msg, String comp){
		addMsg(Message.createError(msg, comp));
	}

	public void addInfMsg(String msg, String comp){
		addMsg(Message.createInfo(msg, comp));
	}

	public void addSucMsg(String msg, String comp){
		addMsg(Message.createSucc(msg, comp));
	}

	public boolean empty(){
		for(List<Message> list:_messages.values()){
			if(list.size()>0)
				return false;
		}
		return true;
	}

	public void clear(){
		_messages.clear();
	}
}
