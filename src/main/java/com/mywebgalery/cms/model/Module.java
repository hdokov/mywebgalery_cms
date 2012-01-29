package com.mywebgalery.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.annotations.Index;
import org.hibernate.criterion.Property;

/**
 *
 * @author ican
 * This is the base entity that holds the data rendered by different render blocks defined as <code>DisplayBlockContribution</code>
 */
@Entity
@Table(name="modules", uniqueConstraints={@UniqueConstraint(columnNames={"appid", "name"})})
//@Audited
public class Module extends Model<Module> {

	/**
	 * The App this module belongs to
	 */
	@Index(name="module_app_index")
	private long appId;

	/**
	 * The name of the module - allowed characters: [a-z0-9_]
	 * This name will be used to insert the module in pages.
	 */
	@Column(columnDefinition="text")
	@Index(name="module_name_index")
	private String name;

	/**
	 * The name of the module that will be shown in the admin area
	 */
	@Column(columnDefinition="text")
	private String displayName;

	/**
	 * Description of the module
	 */
	@Column(columnDefinition="text")
	private String descr;

	/**
	 * The name of the <code>DisplayBlockContribution</code> this module will render.
	 * See <code>AppModule</code> how the blocks are defined
	 */
	@Column(columnDefinition="text")
	private String type;

	/**
	 * Private module properties persisted as JSON string.
	 */
	@Column(columnDefinition="text")
	private String data;

	/**
	 * Custom HTML content if needed
	 */
	@Column(columnDefinition="text")
	private String content;


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

	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	// DAO

	private static final Module INSTANCE = new Module();
	public static Module getInstance(){
		return INSTANCE;
	}

	public Module getByAppAndName(Session s, long app, String name) throws Exception{
		Criteria c = s.createCriteria(getClass());
		c.add(Property.forName("appId").eq(app)).add(Property.forName("name").eq(name));
		return (Module)c.uniqueResult();
	}

}
