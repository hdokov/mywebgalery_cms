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
 * Menus are used to provide links to pages and articles
 * @author ican
 *
 */
@Entity
@Table(name="menus", uniqueConstraints={@UniqueConstraint(columnNames={"appid", "name"})})
//@Audited
public class Menu extends Model<Menu> {

	/**
	 * The application this menu belongs to
	 */
	@Index(name="menu_app_index")
	private long appId;

	/**
	 * Display name of the menu
	 */
	@Column(columnDefinition="text")
	private String name;

	/**
	 * URI for the menu
	 */
	@Column(columnDefinition="text")
	private String uri;

	/**
	 * The menu this menu belongs to
	 */
	@ManyToOne
	@JoinColumn(name="parent", nullable=true, updatable=true, insertable=true,columnDefinition="bigint")
	private Menu parent;

	@OneToMany
	@Cascade({CascadeType.ALL})
	@JoinColumn(name="parent")
	@IndexColumn(name="ordered")
	private List<Menu> submenus = new ArrayList<Menu>();

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
	public Menu getParent() {
		return parent;
	}
	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public List<Menu> getSubmenus() {
		return submenus;
	}
	public void setSubmenus(List<Menu> submenus) {
		this.submenus = submenus;
	}
	public void addSubMenu(Menu c){
		submenus.add(c);
	}

	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Menu && id == ((Menu)obj).id;
	}

	@Override
	public int hashCode() {
		return (int)id;
	}

	// DAO
	private static final Menu INSTANCE = new Menu();
	public static Menu getInstance(){
		return INSTANCE;
	}

	public Menu getRootMenu(Session s, long appId) throws Exception{
		Criteria c = s.createCriteria(getClass());
		c.add(Property.forName("appId").eq(appId)).add(Property.forName("parent").isNull());
		Menu menu = (Menu)c.uniqueResult();
		if(menu == null){
			menu = new Menu();
			menu.setAppId(appId);
			menu.setName("root");
			menu.save(s);
		}
		return menu;
	}
}
