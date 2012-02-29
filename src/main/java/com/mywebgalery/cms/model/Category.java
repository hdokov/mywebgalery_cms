package com.mywebgalery.cms.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.criterion.Property;

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
	@ManyToOne
	@JoinColumn(name="parentCategory", nullable=true, updatable=true, insertable=true)
	private Category parentCategory;

	@OneToMany
	@Cascade({CascadeType.ALL})
	@JoinColumn(name="parentCategory")
	@IndexColumn(name="ordered")
	private List<Category> subcategories = new ArrayList<Category>();

	private Integer ordered;

	public Integer getOrdered() {
		return ordered;
	}
	public void setOrdered(Integer order) {
		this.ordered = order;
	}
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
	public Category getParentCategory() {
		return parentCategory;
	}
	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	public List<Category> getSubcategories() {
		return subcategories;
	}
	public void setSubcategories(List<Category> subcategories) {
		this.subcategories = subcategories;
	}
	public void addSubCategory(Category c){
		subcategories.add(c);
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Category && id == ((Category)obj).id;
	}

	@Override
	public int hashCode() {
		return (int)id;
	}

	// DAO
	private static final Category INSTANCE = new Category();
	public static Category getInstance(){
		return INSTANCE;
	}

	public Category getRootCategory(Session s, long appId) throws Exception{
		Criteria c = s.createCriteria(getClass());
		c.add(Property.forName("appId").eq(appId)).add(Property.forName("parentCategory").isNull());
		Category cat = (Category)c.uniqueResult();
		if(cat == null){
			cat = new Category();
			cat.setAppId(appId);
			cat.setName("root");
			cat.save(s);
		}
		return cat;
	}
}
