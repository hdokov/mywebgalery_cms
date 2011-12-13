package com.mywebgalery.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.envers.Audited;

@Entity
@Audited
public class User extends Model<User> {

	@Column(columnDefinition="text")
	private String name;
	@Column(columnDefinition="text")
	private String sirname;
	@Column(columnDefinition="text")
	private String email;
	@Column(columnDefinition="text")
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
	private Long lastlogin;
	private Integer token;


}
