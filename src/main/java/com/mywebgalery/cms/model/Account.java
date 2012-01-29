package com.mywebgalery.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Index;

/**
 * An account represents a group of users.
 * The names and the emails of the users have to be unique in the account.
 *
 * @author ican
 *
 */
@Entity
@Table(name="accounts", uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
public class Account extends Model<Account>{

	private long owner;

	@Column(columnDefinition="text")
	@Index(name="account_name_index")
	private String name;

	public long getOwner() {
		return owner;
	}
	public void setOwner(long owner) {
		this.owner = owner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
