package com.mywebgalery.cms.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mywebgalery.cms.utils.StringUtils;

@MappedSuperclass
public class Model<T extends Model<?>>  {

	protected static final Logger LOG = LoggerFactory.getLogger(Model.class);
	private static final Map<Class<?>, List<String>> _propertyNamesList = new HashMap<Class<?>, List<String>>();
	private static final Map<Class<?>, String> _propertyNames = new HashMap<Class<?>, String>();

	@Id @Column(columnDefinition="bigserial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;

	@Column(columnDefinition="timestamp without time zone")
	protected Date created;
	@Column(columnDefinition="timestamp without time zone")
	protected Date updated;

	@SuppressWarnings("unchecked")
	public static String propertyNames(Class<? extends Model> clazz){
		String names = _propertyNames.get(clazz);
		if(names == null){
			List<String> list = propertyNamesList(clazz);
			StringBuffer result = new StringBuffer();
			for(String s:list){
				if(result.length()>0)
					result.append(',');
				result.append(s);
			}
			names = result.toString();
			_propertyNames.put(clazz, names);
		}
		return names;
	}

	@SuppressWarnings("unchecked")
	public static List<String> propertyNamesList(Class<? extends Model> clazz){
		List<String> result = _propertyNamesList.get(clazz);
		if(result == null){
			result = new ArrayList<String>();
			_propertyNamesList.put(clazz, result);
			for(Field f : clazz.getDeclaredFields()){
				try {
					if(Modifier.isStatic(f.getModifiers()) || Modifier.isTransient(f.getModifiers()))
						continue;

					String name = f.getName();
					if(name.startsWith("_"))
						name = name.substring(1);

					result.add(name);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
		return result;
	}

	public List<String> getPropertyNames(){
		return propertyNamesList(getClass());
	}

	public void mapFields(Map<String, String> map){
		Class<?> clazz = getClass();
		String prefix = clazz.getSimpleName().toLowerCase()+".";
		for(Field f : clazz.getDeclaredFields()){
			try {
				if(Modifier.isStatic(f.getModifiers()) || Modifier.isTransient(f.getModifiers()))
					continue;

				String name = f.getName();
				if(name.startsWith("_"))
					name = name.substring(1);

				f.setAccessible(true);
				Object val = f.get(this);
				if(val != null)
					map.put(prefix+name, String.valueOf(val));
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}
		map.put(prefix+"id", String.valueOf(id));
		map.put(prefix+"code", getCode());
	}

	@SuppressWarnings("unchecked")
	public static void mapDummyFields(Map<String, String> map, Class<? extends Model> clazz){
		String prefix = clazz.getSimpleName().toLowerCase()+".";
		for(Field f : clazz.getDeclaredFields()){
			try {
				if(Modifier.isStatic(f.getModifiers()) || Modifier.isTransient(f.getModifiers()))
					continue;

				String name = f.getName();
				if(name.startsWith("_"))
					name = name.substring(1);

				map.put(prefix+name, name);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}
		map.put(prefix+"id", "0");
		map.put(prefix+"code", StringUtils.getCode(0L));
	}

	public String getCode(){
		return StringUtils.getCode(id);
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}


	// DAO METHODS
	public void delete(Session s) throws Exception {
		try {
			s.delete(this);
		} catch (Exception e) {
			throw new Exception("Cannot execute querry", e);
		}
	}

	@SuppressWarnings("unchecked")
	public T findById(Session s, Long id) throws EntityNotFoundException, Exception {
		T result = null;
		try {
			result = (T) s.load(getClass(), id);
		} catch (Exception e) {
			throw new Exception("Cannot execute querry", e);
		}
		if (result == null)
			throw new EntityNotFoundException();
		return result;
	}

	@SuppressWarnings("unchecked")
	public T get(Session s, Long id) throws Exception {
		T result = null;
		try {
			result = (T) s.get(getClass(), id);
		} catch (Exception e) {
			throw new Exception("Cannot execute querry", e);
		}
		return result;
	}

	public void save(Session s) throws EntityExistsException, Exception {
		try {
			s.save(this);
		} catch (Exception e) {
			throw new Exception("Cannot execute querry", e);
		}
	}

	public void saveOrUpdate(Session s) throws Exception {
		try {
			s.saveOrUpdate(this);
		} catch (Exception e) {
			throw new Exception("Cannot execute querry", e);
		}
	}

	public void update(Session s) throws EntityNotFoundException, Exception {
		try {
			s.update(this);
		} catch (Exception e) {
			throw new Exception("Cannot execute querry", e);
		}
	}

	public int deleteById(Session s, Long id) throws EntityNotFoundException, Exception {
		T entity = findById(s, id);
		s.delete(entity);
		return 1;
	}

	@SuppressWarnings("unchecked")
	public int deleteByProperty(Session s, Object... properties) throws Exception {
		Criteria c = s.createCriteria(getClass());

		if(properties != null && properties.length > 1){
			if(properties.length % 2 != 0)
				throw new Exception("Properties are invalid. Cannot make name-value pairs.");

			for(int i = 0; i < properties.length / 2;i++){
				c.add(Property.forName(properties[i*2].toString()).eq(properties[(i*2)+1]));
			}
		}
		List<T> list = c.list();
		if(list == null)
			return 0;
		for(T o:list)
			s.delete(o);

		return list.size();
	}

	@SuppressWarnings("unchecked")
	public List<T> findByProperty(Session s, boolean like, Object... properties) throws Exception {
		Criteria c = s.createCriteria(getClass());

		if(properties != null && properties.length > 1){
			if(properties.length % 2 != 0)
				throw new Exception("Properties are invalid. Cannot make name-value pairs.");

			for(int i = 0; i < properties.length / 2;i++){
				if(like){
					Object value = properties[(i*2)+1];
					if(value instanceof String){
						c.add(Property.forName(properties[i*2].toString()).like((String) value, MatchMode.ANYWHERE).ignoreCase());
					}else{
						c.add(Property.forName(properties[i*2].toString()).like(value));
					}
				}else{
					c.add(Property.forName(properties[i*2].toString()).eq(properties[(i*2)+1]));
				}
			}
		}
		List<T> list = c.list();
		return list;
	}

}
