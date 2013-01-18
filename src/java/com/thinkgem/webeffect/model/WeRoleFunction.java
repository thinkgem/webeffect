package com.thinkgem.webeffect.model;

/**
 * WeRoleFunction entity. @author MyEclipse Persistence Tools
 */

public class WeRoleFunction implements java.io.Serializable {

	// Fields

	private WeRoleFunctionId id;

	// Constructors

	/** default constructor */
	public WeRoleFunction() {
	}

	/** full constructor */
	public WeRoleFunction(WeRoleFunctionId id) {
		this.id = id;
	}

	// Property accessors

	public WeRoleFunctionId getId() {
		return this.id;
	}

	public void setId(WeRoleFunctionId id) {
		this.id = id;
	}

}