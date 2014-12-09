package com.mhdanh.mytemplate.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "uploadtemplate")
public class UploadTemplate {
	
	@Id
	@GenericGenerator(name="increment" , strategy="increment")
	@GeneratedValue(generator="increment")
	@Column(name = "id")
	private int id; 
	
	@Column
	private String name;
	
	@Column(nullable = false,unique = true)
	private String link;
	
	@Column
	private Date dateCreated;
	
	@ManyToOne(targetEntity = Category.class)
	private Category category;
	
	

	public UploadTemplate() {
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



	public String getLink() {
		return link;
	}



	public void setLink(String link) {
		this.link = link;
	}



	public Date getDateCreated() {
		return dateCreated;
	}



	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}



	public Category getCategory() {
		return category;
	}



	public void setCategory(Category category) {
		this.category = category;
	}

}
