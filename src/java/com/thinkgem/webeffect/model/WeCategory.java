package com.thinkgem.webeffect.model;

import java.util.HashSet;
import java.util.Set;

/**
 * WeCategory entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings({ "unchecked", "serial" })
public class WeCategory implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String description;
	private Integer weight;	
	private Set weEffects = new HashSet(0);

	// Constructors

	/** default constructor */
	public WeCategory() {
	}

	/** minimal constructor */
	public WeCategory(String name, String description, Integer weight) {
		this.name = name;
		this.description = description;
		this.weight = weight;
	}

	/** full constructor */
	public WeCategory(String name, String description, Integer weight,
			Set weEffects) {
		this.name = name;
		this.description = description;
		this.weight = weight;
		this.weEffects = weEffects;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getWeight() {
		return this.weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Set getWeEffects() {
		return this.weEffects;
	}

	public void setWeEffects(Set weEffects) {
		this.weEffects = weEffects;
	}

}