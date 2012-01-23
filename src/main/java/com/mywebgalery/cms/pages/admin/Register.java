package com.mywebgalery.cms.pages.admin;

import javax.persistence.EntityExistsException;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.hibernate.Session;

import com.mywebgalery.cms.base.BasePage;
import com.mywebgalery.cms.model.Account;
import com.mywebgalery.cms.model.User;
import com.mywebgalery.cms.utils.StringUtils;

public class Register extends BasePage {

	@Persist
	private User _user;

	@Property
	private String _confirm;

	public User getUser() {
		if(_user == null)
			_user = new User();

		return _user;
	}

	@OnEvent(component="form")
	public Object submit(){
		try {
			if(!validate())
				return null;

			Session s = getTransactionManager().getSession();
			s.beginTransaction();
			_user.setEmail(_user.getEmail().trim().toLowerCase());
			_user.setName(_user.getName().trim());
			_user.setFirstname(_user.getFirstname().trim());
			_user.setLastname(_user.getLastname().trim());


			if(!_user.isNameFree(s, _user.getName(), null)){
				addErrMsg(getMessages().get("error.user_name_exists"), "name");
				return null;
			}

			Account a = new Account();
			a.setName(_user.getName());
			a.save(s);
			_user.setAccountId(a.getId());
			_user.setAdmin(true);
			_user.save(s);
			getSessionData().setUser(_user);
			_user = null;
			//MailSender.sendSSLMessage(new String[]{"admin@mywebgalery.com"}, "New account "+_user.getName(), "New account has been created: "+_user.getEmail(), "support@mywebgalery.com");
			return "admin/myprofile";
		} catch (EntityExistsException e) {
			e.printStackTrace();
			addErrMsg(getMessages().get("error.account_name_exists"), null);
		} catch (Exception e) {
			e.printStackTrace();
			addErrMsg(e.getMessage(), null);
		}
		return null;
	}

	private boolean validate() {
		if(StringUtils.isBlank(_user.getName())){
			addErrMsg("First name is required", "name");
		}
		if(StringUtils.isBlank(_user.getFirstname())){
			addErrMsg("Last name is required", "firstname");
		}
		if(StringUtils.isBlank(_user.getLastname())){
			addErrMsg("Last name is required", "lastname");
		}
		if(StringUtils.isBlank(_user.getEmail())){
			addErrMsg("Email is required", "email");
		}
		if(StringUtils.isBlank(_user.getPass())){
			addErrMsg("Password is required", "pass");
		} else if (_user.getPass().length() < 5){
			addErrMsg("Password must be at least 5 symbols", "pass");
		}
		if(StringUtils.isBlank(_confirm)){
			addErrMsg("Confirm password is required", "confirm");
		} else if (!_confirm.equals(_user.getPass())){
			addErrMsg("Password and Confirm password don't match", "confirm");
		}
		if(!getAppMessages().empty()){
			addErrMsg("Please fix the following errors", null);
			return false;
		}
		return true;
	}


}
