package com.mywebgalery.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

/**
 *
 * @author ican
 * This is the base entity that holds the data rendered by different render blocks defined as <code>DisplayBlockContribution</code>
 */
@Entity
@Table(name="modules")
@Audited
public class Module extends Model<Module> {

	/**
	 * The page this module belongs to
	 */
	private long pageId;

	/**
	 * The name of the block in which this module will be rendered
	 */
	@Column(columnDefinition="text") private String position;

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


	public long getPageId() {
		return pageId;
	}
	public void setPageId(long pageId) {
		this.pageId = pageId;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
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


}
