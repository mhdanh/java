package com.mhdanh.mytemplate.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "template")
public class Template {

	public static enum TEMPLATE_STATUS {
		WAITING, VIEWED, PUBLISHED
	}

	public static enum UNIT_MONEY {
		VND
	}

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

	@Column(columnDefinition = "text")
	private String log;

	@Column(nullable = false, unique = true)
	private String link;

	@Column(columnDefinition = "int default 0")
	private Integer cost;

	@Column(columnDefinition = "int default 0")
	private Integer sellOff;

	@Column
	@Enumerated(EnumType.STRING)
	private UNIT_MONEY unitMoney;

	@Column(columnDefinition = "int default 0")
	private Integer buy;

	@Column
	private Date dateCreated;

	@Column
	private Date dateModified;

	@Column
	private String thumbnail;

	@Column
	@Enumerated(EnumType.STRING)
	private TEMPLATE_STATUS status;

	@ManyToOne(targetEntity = Category.class)
	private Category category;

	@ManyToOne(targetEntity = Account.class)
	private Account owner;

	public Template() {
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

	public Integer getCost() {
		if(cost == null){
			return 0;
		}
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public Integer getSellOff() {
		if(sellOff == null){
			return 0;
		}
		return sellOff;
	}

	public void setSellOff(Integer sellOff) {
		this.sellOff = sellOff;
	}

	public UNIT_MONEY getUnitMoney() {
		return unitMoney;
	}

	public void setUnitMoney(UNIT_MONEY unitMoney) {
		this.unitMoney = unitMoney;
	}

	public Integer getBuy() {
		if (buy == null) {
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

	public TEMPLATE_STATUS getStatus() {
		return status;
	}

	public void setStatus(TEMPLATE_STATUS status) {
		this.status = status;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

}
