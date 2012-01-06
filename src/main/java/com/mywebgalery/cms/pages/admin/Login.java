package com.mywebgalery.cms.pages.admin;

import java.util.Date;

import javax.persistence.EntityExistsException;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.hibernate.Session;

import com.mywebgalery.cms.base.BasePage;
import com.mywebgalery.cms.model.User;

public class Login extends BasePage {

	@Property
	private String _name;

	@Property
	private String _pass;

	@OnEvent(component="form")
	public Object submit(){
		try {
			Session s = getTransactionManager().getSession();
			s.beginTransaction();
			User u = User.getInstance().login(s, _name, _pass, true);
			if(u == null){
				addErrMsg(getMessages().get("error.invalid_login"), null);
				return null;
			}
			u.setLastLoginAdmin(new Date());
			u.save(s);
			s.evict(u);
			getSessionData().setUser(u);
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

}
