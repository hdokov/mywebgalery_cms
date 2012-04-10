package com.mywebgalery.cms.model;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.annotations.Index;

import com.mywebgalery.cms.utils.StringUtils;

@Entity
//@Audited
@Table(name="users", uniqueConstraints={
			@UniqueConstraint(columnNames={"accountid", "name"}),
			@UniqueConstraint(columnNames={"accountid","email"}),
			@UniqueConstraint(columnNames={"token"})
		})
public class User extends Model<User> {

	@Column(columnDefinition="text", nullable=false)
	@Index(name="user_name_index")
	private String name;

	@Column(columnDefinition="text", nullable=false)
	private String firstname;

	@Column(columnDefinition="text", nullable=false)
	private String lastname;

	@Column(columnDefinition="text", nullable=false)
	@Index(name="user_email_index")
	private String email;

	@Column(columnDefinition="text", nullable=false)
	private String pass;

	@Column(columnDefinition="text")
	private String country;

	@Column(columnDefinition="text")
	private String city;

	@Column(columnDefinition="text")
	private String zip;

	@Column(columnDefinition="text")
	private String address;

	@Column(columnDefinition="text")
	private String phone;

	@Column(columnDefinition="timestamp without time zone")
	private Date lastLoginUser;

	@Column(columnDefinition="timestamp without time zone")
	private Date lastLoginAdmin;

	@Column(columnDefinition="text")
	private String token;

	private boolean admin;

	private String privileges;

	@Index(name="user_account_index")
	private long accountId;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public Date getLastLoginUser() {
		return lastLoginUser;
	}
	public void setLastLoginUser(Date lastLoginUser) {
		this.lastLoginUser = lastLoginUser;
	}
	public Date getLastLoginAdmin() {
		return lastLoginAdmin;
	}
	public void setLastLoginAdmin(Date lastLoginAdmin) {
		this.lastLoginAdmin = lastLoginAdmin;
	}
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPrivileges() {
		return privileges;
	}
	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}

	public boolean isAnonymous(){
		return getId() == 0;
	}

	public boolean can(String action){
		if(isAnonymous() || StringUtils.isBlank(privileges))
			return false;

		return privileges.indexOf(action) > -1;
	}


	// DAO

	private static final User INSTANCE = new User();
	public static User getInstance(){
		return INSTANCE;
	}

	public boolean isNameFree(Session s, String name, Long appId) throws Exception{
		try {
			String sql = "select count(*) from users where lower(name)=?";
			if(appId == null){
				sql += " and admin";
			} else {
				sql += " and appid="+appId;
			}

			SQLQuery q = s.createSQLQuery(sql);
			q.setString(0, name);
			BigInteger cnt = (BigInteger)q.uniqueResult();
			return cnt.intValue() == 0;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public User login(Session s, String name, String pass, boolean admin) throws Exception {
		String sql = "select * from users where lower(name) = ?";
		SQLQuery q = s.createSQLQuery(sql);
		q.setString(0, name);
		//q.setBoolean(1, admin);
		q.addEntity(getClass());
		User u = (User)q.uniqueResult();
		if(u != null && !pass.equals(u.getPass())){
			return null;
		}
		if(admin)
			u.setLastLoginAdmin(new Date());
		else
			u.setLastLoginUser(new Date());

		u.save(s);
		s.evict(u);
		return u;
	}

}
