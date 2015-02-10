package com.mhdanh.mytemplate.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "category")
public class Category {
	
	@Id
	@GenericGenerator(name="increment" , strategy="increment")
	@GeneratedValue(generator="increment")
	@Column(name = "id")
	private int id; 
	
	@Column(name = "name",unique = true,nullable = false)
	private String name;

	@Override
	public boolean equals(Object o){
		if(o == null) {
			return false;
		}
		if(!(o instanceof Category)) {
			return false;
		}
		Category otherCategory = (Category) o;
		return this.getId() == otherCategory.getId();
	}
	
	@Override
	public int hashCode(){
		return this.getId();
	}

	public Category() {
	}
	
	public Category(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
