package com.mhdanh.mytemplate.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "role")
public class Role {
	public static enum ROLE_NAME {ADMIN,SUPPER_USER,USER}
	
	@Id
	@GenericGenerator(name="increment" , strategy="increment")
	@GeneratedValue(generator="increment")
	@Column(name = "id")
	private int id;
	
	@Column(name = "name",length = 32,nullable = false,unique = true)
	@Enumerated(EnumType.STRING)
	private ROLE_NAME name;
	
	@ManyToMany(mappedBy = "roles")
	private List<Account> accounts;
	
	@Override
	public boolean equals(Object o){
		if(o == null) {
			return false;
		}
		if(!(o instanceof Role)) {
			return false;
		}
		Role otherRole= (Role) o;
		return this.getId() == otherRole.getId();
	}
	
	@Override
	public int hashCode(){
		return this.getId();
	}
	
	public Role() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name.toString();
	}

	public void setName(ROLE_NAME name) {
		this.name = name;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

}
