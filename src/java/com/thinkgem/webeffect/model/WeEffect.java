package com.thinkgem.webeffect.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * WeEffect entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings({ "unchecked", "serial" })
public class WeEffect implements java.io.Serializable {

	// Fields

	private Integer id;
	private WeFile weFile;
	private WeFile weFileNew;
	private WeCategory weCategory;
	private String categoryName;
	private String title;
	private String description;
	private String author;
	private String username;
	private Timestamp created;
	private Timestamp updated;
	private Integer clickNum;
	private Integer downNum;
	private Set weComments = new HashSet(0);

	// Constructors

	/** default constructor */
	public WeEffect() {
	}

	/** minimal constructor */
	public WeEffect(WeFile weFile, WeCategory weCategory, String categoryName,
			String title, String description, String author, String username,
			Timestamp created, Timestamp updated, Integer clickNum,
			Integer downNum) {
		this.weFile = weFile;
		this.weCategory = weCategory;
		this.categoryName = categoryName;
		this.title = title;
		this.description = description;
		this.author = author;
		this.username = username;
		this.created = created;
		this.updated = updated;
		this.clickNum = clickNum;
		this.downNum = downNum;
	}

	/** full constructor */
	public WeEffect(WeFile weFile, WeCategory weCategory, String categoryName,
			String title, String description, String author, String username,
			Timestamp created, Timestamp updated, Integer clickNum,
			Integer downNum, Set weComments) {
		this.weFile = weFile;
		this.weCategory = weCategory;
		this.categoryName = categoryName;
		this.title = title;
		this.description = description;
		this.author = author;
		this.username = username;
		this.created = created;
		this.updated = updated;
		this.clickNum = clickNum;
		this.downNum = downNum;
		this.weComments = weComments;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WeFile getWeFile() {
		return this.weFile;
	}

	public void setWeFile(WeFile weFile) {
		this.weFile = weFile;
	}

	public WeCategory getWeCategory() {
		return this.weCategory;
	}

	public void setWeCategory(WeCategory weCategory) {
		this.weCategory = weCategory;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getUpdated() {
		return this.updated;
	}

	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}

	public Integer getClickNum() {
		return this.clickNum;
	}

	public void setClickNum(Integer clickNum) {
		this.clickNum = clickNum;
	}

	public Integer getDownNum() {
		return this.downNum;
	}

	public void setDownNum(Integer downNum) {
		this.downNum = downNum;
	}

	public Set getWeComments() {
		return this.weComments;
	}

	public void setWeComments(Set weComments) {
		this.weComments = weComments;
	}

	public WeFile getWeFileNew() {
		return weFileNew;
	}

	public void setWeFileNew(WeFile weFileNew) {
		this.weFileNew = weFileNew;
	}

}