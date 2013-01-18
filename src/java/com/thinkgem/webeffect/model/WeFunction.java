package com.thinkgem.webeffect.model;

import java.util.HashSet;
import java.util.Set;

/**
 * WeFunction entity. @author MyEclipse Persistence Tools
 */

public class WeFunction implements java.io.Serializable {

	// Fields

	private Integer id;
	private String uri;
	private String description;
	private Set weRoleFunctions = new HashSet(0);

	// Constructors

	/** default constructor */
	public WeFunction() {
	}

	/** minimal constructor */
	public WeFunction(String uri, String description) {
		this.uri = uri;
		this.description = description;
	}

	/** full constructor */
	public WeFunction(String uri, String description, Set weRoleFunctions) {
		this.uri = uri;
		this.description = description;
		this.weRoleFunctions = weRoleFunctions;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set getWeRoleFunctions() {
		return this.weRoleFunctions;
	}

	public void setWeRoleFunctions(Set weRoleFunctions) {
		this.weRoleFunctions = weRoleFunctions;
	}

}