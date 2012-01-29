package com.mywebgalery.cms.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.tapestry5.ioc.Resource;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.annotations.Index;
import org.hibernate.criterion.Property;

/**
 * An App represents one website. Categories, Pages & Modules are contained in an App.
 * @author ican
 *
 */
@Entity
@Table(name="apps", uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
public class App extends Model<App> implements Resource {

	/**
	 * The user that created the app.
	 * The user will be one of the admins for the account.
	 */
	private long owner;

	/**
	 * The account that this app belongs to.
	 * The owner of the app may be different than the owner of the account.
	 */
	@Index(name="app_account_index")
	private long accountId;

	/**
	 * The unique app name. It will be the subdomain used for the app so it should comform to domain name rules.
	 * Allowed characters: [a-z0-9_-]
	 */
	@Column(columnDefinition="text")
	@Index(name="app_name_index")
	private String name;

	/**
	 * The display name of the app.
	 * The HTML <code>title</code> tag will be set as: <app title> - <page title>
	 */
	@Column(columnDefinition="text")
	private String title;

	/**
	 * HTML representing the common page wrap for the app
	 */
	@Column(columnDefinition="text")
	private String wrap;

	/**
	 * Template name
	 */
	@Column(columnDefinition="text")
	private String template;

	/**
	 * Color theme name
	 */
	@Column(columnDefinition="text")
	private String theme;



	/**
	 * App description - will be set as HTML description meta tag
	 */
	@Column(columnDefinition="text")
	private String descr;

	/**
	 * App keywords - will be set as HTML keywords meta tag
	 */
	@Column(columnDefinition="text")
	private String keywords;

	public long getOwner() {
		return owner;
	}
	public void setOwner(long owner) {
		this.owner = owner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public String getWrap() {
		return wrap;
	}
	public void setWrap(String wrap) {
		this.wrap = wrap;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}


	// DAO
	private static final App INSTANCE = new App();
	public static App getInstance(){
		return INSTANCE;
	}

	public App get(Session s, String name) throws Exception{
		Criteria c = s.createCriteria(getClass());
		c.add(Property.forName("name").eq(name));
		return (App)c.uniqueResult();
	}


	// Resource methods
	public boolean exists() { return true; }

	public Resource forFile(String relativePath) { return this; }

	public Resource forLocale(Locale locale) { return this; }

	public String getFile() { return null; }

	public String getFolder() { return null; }

	public String getPath() { return null; }

	public InputStream openStream() throws IOException {
		if(wrap == null)
			return null;
		return new ByteArrayInputStream(wrap.getBytes());
	}

	public URL toURL() { return null; }

	public Resource withExtension(String extension) { return this; }

}
