package com.mywebgalery.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;


@Entity
@Table(name="pages")
@Audited
public class Page extends Model<Page> {

	private long appId;

	@Column(columnDefinition="text")
	private String title;
	@Column(columnDefinition="text")
	private String url;
	@Column(columnDefinition="text")
	private String type;

	private Long templateId;


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public boolean isTemplate(){
		return templateId == null;
	}
}
