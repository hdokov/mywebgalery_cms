package com.mywebgalery.cms.base;

import org.apache.tapestry5.annotations.PageDetached;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.mywebgalery.cms.model.TransactionManager;
import com.mywebgalery.cms.model.User;
import com.mywebgalery.cms.services.AppMessages;
import com.mywebgalery.cms.services.UserSessionData;

public class BaseComponent {

	@Inject private Logger _log;

	@Inject private Messages _messages;

	@SessionState private AppMessages _appMessages;
	@SessionState private UserSessionData _sessionData;

	private TransactionManager _transactionManager;

	public Logger getLog() {return _log;}

	public TransactionManager getTransactionManager(){
		if(_transactionManager == null)
			_transactionManager = new TransactionManager();

		return _transactionManager;
	}

	@PageDetached
	protected void detachPage(){
		if(_transactionManager != null)
			try{
				_transactionManager.commit();
				_transactionManager.close();
			}catch(Exception e){
				getLog().warn("Unknown error - " + e.getMessage(), e);
			}
	}

	public String translate(String key){
		return _messages.get(key);
	}

	public String translate(String key, Object... params){
		return _messages.format(key, params);
	}

	public AppMessages getAppMessages() {
		return _appMessages;
	}

	public void addErrMsg(String msg, String comp){
		getAppMessages().addErrMsg(msg, comp);
	}

	public void addInfMsg(String msg, String comp){
		getAppMessages().addInfMsg(msg, comp);
	}

	public void addSucMsg(String msg, String comp){
		getAppMessages().addSucMsg(msg, comp);
	}

	public UserSessionData getSessionData() {
		return _sessionData;
	}

	public User getUser() {
		return getSessionData().getUser();
	}


}
