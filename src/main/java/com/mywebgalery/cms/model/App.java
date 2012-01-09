package com.mywebgalery.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="apps")
public class App extends Model<App>{

	private long owner;
	private long accountId;

	@Column(columnDefinition="text")
	private String name;
	@Column(columnDefinition="text")
	private String title;
	@Column(columnDefinition="text")
	private String descr;
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


	// DAO

	private static final App INSTANCE = new App();
	public static App getInstance(){
		return INSTANCE;
	}

}
