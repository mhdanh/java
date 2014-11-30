package com.mhdanh.mytemplate.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="account")
public class Account {
	
	@Id
	@GenericGenerator(name="increment" , strategy="increment")
	@GeneratedValue(generator="increment")
	@Column(name = "id")
	private int id; 
	
	@Column(name = "username", length = 32, nullable = false, unique = true)
	private String username;
	
	@Column(name = "password", length = 32, nullable = false)
	private String password;

	@ManyToMany(targetEntity = Role.class)
	@JoinTable(name = "account_role",joinColumns = {@JoinColumn(name = "account_id")},inverseJoinColumns = {@JoinColumn(name = "role_id")})
	@Cascade(value={CascadeType.DELETE})
	private List<Role> roles;
	
	public Account() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}
