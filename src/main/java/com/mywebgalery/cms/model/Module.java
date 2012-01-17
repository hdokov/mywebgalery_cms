package com.mywebgalery.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author ican
 * This is the base entity that holds the data rendered by different render blocks defined as <code>DisplayBlockContribution</code>
 */
@Entity
@Table(name="modules")
//@Audited
public class Module extends Model<Module> {

	/**
	 * The page this module belongs to
	 */
	private long appId;

	/**
	 * The display name of the module
	 */
	@Column(columnDefinition="text") private String name;

	/**
	 * Description of the module
	 */
	@Column(columnDefinition="text") private String descr;

	/**
	 * The name of the <code>DisplayBlockContribution</code> this module will render.
	 * See <code>AppModule</code> how the blocks are defined
	 */
	@Column(columnDefinition="text") private String type;

	/**
	 * Private module properties persisted as JSON string.
	 */
	@Column(columnDefinition="text") private String data;

	/**
	 * Custom HTML content if needed
	 */
	@Column(columnDefinition="text") private String content;


	public long getAppId() {
		return appId;
	}
	public void setAppId(long appId) {
		this.appId = appId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}

	// DAO

	private static final Module INSTANCE = new Module();
	public static Module getInstance(){
		return INSTANCE;
	}


}
