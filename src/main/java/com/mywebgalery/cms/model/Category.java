package com.mywebgalery.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Index;

/**
 * Categories are used to group pages and articles
 * @author ican
 *
 */
@Entity
@Table(name="categories", uniqueConstraints={@UniqueConstraint(columnNames={"appid", "name"})})
//@Audited
public class Category extends Model<Category> {

	/**
	 * The application this category belongs to
	 */
	@Index(name="category_app_index")
	private long appId;

	/**
	 * Display name of the category
	 */
	@Column(columnDefinition="text")
	private String name;

	/**
	 * The category this category belongs to
	 */
	@Index(name="category_parent_index")
	private Long parentCategory;

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
	public Long getParentCategory() {
		return parentCategory;
	}
	public void setParentCategory(Long parentCategory) {
		this.parentCategory = parentCategory;
	}

	// DAO
	private static final Category INSTANCE = new Category();
	public static Category getInstance(){
		return INSTANCE;
	}

}
