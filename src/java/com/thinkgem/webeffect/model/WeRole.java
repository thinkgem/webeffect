package com.thinkgem.webeffect.model;

import java.util.HashSet;
import java.util.Set;

/**
 * WeRole entity. @author MyEclipse Persistence Tools
 */

public class WeRole implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Set weRoleFunctions = new HashSet(0);

	// Constructors

	/** default constructor */
	public WeRole() {
	}

	/** minimal constructor */
	public WeRole(String name) {
		this.name = name;
	}

	/** full constructor */
	public WeRole(String name, Set weRoleFunctions) {
		this.name = name;
		this.weRoleFunctions = weRoleFunctions;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getWeRoleFunctions() {
		return this.weRoleFunctions;
	}

	public void setWeRoleFunctions(Set weRoleFunctions) {
		this.weRoleFunctions = weRoleFunctions;
	}

}