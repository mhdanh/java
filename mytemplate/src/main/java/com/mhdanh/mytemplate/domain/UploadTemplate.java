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
	@GenericGenerator(name = "increment", strategy = "increment")
	@GeneratedValue(generator = "increment")
	@Column(name = "id")
	private int id;

	@Column
	private String title;

	@Column
	private String fileName;

	@Column(columnDefinition = "text")
	private String description;

	@Column(nullable = false, unique = true)
	private String link;

	@Column
	private String cost;

	@Column
	private String sellOff;

	@Column(columnDefinition="int default 0")
	private Integer buy;

	@Column
	private Date dateCreated;
	
	@Column
	private Date dateModified;

	@Column
	private String thumbnail;

	@ManyToOne(targetEntity = Category.class)
	private Category category;

	@ManyToOne(targetEntity = Account.class)
	private Account owner;

	public UploadTemplate() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Account getOwner() {
		return owner;
	}

	public void setOwner(Account owner) {
		this.owner = owner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getSellOff() {
		return sellOff;
	}

	public void setSellOff(String sellOff) {
		this.sellOff = sellOff;
	}

	public Integer getBuy() {
		if(buy == null){
			return 0;
		}
		return buy;
	}

	public void setBuy(Integer buy) {
		this.buy = buy;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

}
