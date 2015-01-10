package com.mhdanh.mytemplate.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "account")
public class Account {

	public static enum ACCOUNT_STATUS {
		ACTIVE, WAITING, BLOCKED
	}

	@Id
	@GenericGenerator(name = "increment", strategy = "increment")
	@GeneratedValue(generator = "increment")
	@Column(name = "id")
	private int id;

	@Column(name = "username", length = 32, unique = true)
	private String username;

	@Column(name = "password", length = 32)
	private String password;

	@Column(unique = true)
	private String email;

	@Column(nullable = true)
	private Date dateCreated;

	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private ACCOUNT_STATUS status;

	@Column(nullable = true, unique = true)
	private String token;
	
	@Column(nullable = true, unique = true,length = 32)
	private String keyRecoverPassword;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private Date dateModified;

	@ManyToMany(targetEntity = Role.class)
	@JoinTable(name = "account_role", joinColumns = { @JoinColumn(name = "account_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	@Cascade(value = { CascadeType.DELETE })
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public ACCOUNT_STATUS getStatus() {
		return status;
	}

	public void setStatus(ACCOUNT_STATUS status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public String getKeyRecoverPassword() {
		return keyRecoverPassword;
	}

	public void setKeyRecoverPassword(String keyRecoverPassword) {
		this.keyRecoverPassword = keyRecoverPassword;
	}
}
