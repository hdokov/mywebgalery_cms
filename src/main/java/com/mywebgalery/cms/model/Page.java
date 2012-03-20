package com.mywebgalery.cms.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.tapestry5.ioc.Resource;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.annotations.Index;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;

import com.mywebgalery.cms.model.helpers.CategoryPageBean;
import com.mywebgalery.cms.model.helpers.CategoryPageTransformer;


@Entity
@Table(name="pages", uniqueConstraints={@UniqueConstraint(columnNames={"appid", "url"})})
//@Audited
public class Page extends Model<Page> implements Resource {

	public static final String PAGE_TYPE_STATIC = "static";
	public static final String PAGE_TYPE_ARTICLE = "article";
	//public static final String PAGE_TYPE_STATIC = "static";

	@Index(name="page_app_index")
	private long appId;

	@Column(columnDefinition="text")
	private String title;

	@Column(columnDefinition="text")
	@Index(name="page_url_index")
	private String url;

	@Index(name="page_type_index")
	@Column(columnDefinition="text")
	private String type;

	@Column(columnDefinition="text")
	private String content;

	@Index(name="page_category_index")
	private Long categoryId;

	private boolean defaultPage;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}


	public boolean isDefaultPage() {
		return defaultPage;
	}

	public void setDefaultPage(boolean defaultPage) {
		this.defaultPage = defaultPage;
	}


	// DAO
	private static final Page INSTANCE = new Page();
	public static Page getInstance(){
		return INSTANCE;
	}

	public Page getDefault(Session s, long app) throws Exception {
		Criteria c = s.createCriteria(getClass());
		c.add(Property.forName("appId").eq(app)).add(Property.forName("defaultPage").eq(Boolean.TRUE));
		return (Page)c.uniqueResult();
	}

	public Page getByName(Session s, long app, String name) throws Exception {
		Criteria c = s.createCriteria(getClass());
		c.add(Property.forName("appId").eq(app)).add(Property.forName("url").eq(name));
		return (Page)c.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Page> getByType(Session s, long app, String type) throws Exception {
		Criteria c = s.createCriteria(getClass());
		c.add(Property.forName("appId").eq(app)).add(Property.forName("type").eq(type));
		c.addOrder(Order.asc("title"));
		return c.list();
	}

	@SuppressWarnings("unchecked")
	public List<Page> getByCategoryAndType(Session s, long categoryId, String type) throws Exception {
		Criteria c = s.createCriteria(getClass());
		c.add(Property.forName("categoryId").eq(categoryId)).add(Property.forName("type").eq(type));
		c.addOrder(Order.asc("title"));
		return c.list();
	}

	public void setDefault(Session s, long app, long pageId) throws Exception {
		String sql = "update pages set defaultpage = (id = ?) where appid = ?";
		SQLQuery q = s.createSQLQuery(sql);
		q.setLong(0, pageId);
		q.setLong(1, app);
		q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<CategoryPageBean> getPagesList(Session s, long appId){
		SQLQuery q = s.createSQLQuery("select p.url as pageid, p.title as pagename, c.id as catid, c.name as catname, c.parentcategory as parent from categories c left join pages p on c.id = p.categoryid where c.appid = ?  order by c.parentcategory, c.ordered, p.id");
		q.setLong(0, appId);
		//CategoryPageBean root = new CategoryPageBean();
		q.setResultTransformer(new CategoryPageTransformer());
		return q.list();
	}

	// Resource methods
	public boolean exists() { return true; }

	public Resource forFile(String relativePath) { return this; }

	public Resource forLocale(Locale locale) { return this; }

	public String getFile() { return null; }

	public String getFolder() { return null; }

	public String getPath() { return null; }

	public InputStream openStream() throws IOException {
		if(content == null)
			return null;
		return new ByteArrayInputStream(content.getBytes());
	}

	public URL toURL() { return null; }

	public Resource withExtension(String extension) { return this; }

}
