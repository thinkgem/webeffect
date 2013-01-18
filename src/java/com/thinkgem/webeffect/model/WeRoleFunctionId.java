package com.thinkgem.webeffect.model;

/**
 * WeRoleFunctionId entity. @author MyEclipse Persistence Tools
 */

public class WeRoleFunctionId implements java.io.Serializable {

	// Fields

	private WeRole weRole;
	private WeFunction weFunction;

	// Constructors

	/** default constructor */
	public WeRoleFunctionId() {
	}

	/** full constructor */
	public WeRoleFunctionId(WeRole weRole, WeFunction weFunction) {
		this.weRole = weRole;
		this.weFunction = weFunction;
	}

	// Property accessors

	public WeRole getWeRole() {
		return this.weRole;
	}

	public void setWeRole(WeRole weRole) {
		this.weRole = weRole;
	}

	public WeFunction getWeFunction() {
		return this.weFunction;
	}

	public void setWeFunction(WeFunction weFunction) {
		this.weFunction = weFunction;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WeRoleFunctionId))
			return false;
		WeRoleFunctionId castOther = (WeRoleFunctionId) other;

		return ((this.getWeRole() == castOther.getWeRole()) || (this
				.getWeRole() != null
				&& castOther.getWeRole() != null && this.getWeRole().equals(
				castOther.getWeRole())))
				&& ((this.getWeFunction() == castOther.getWeFunction()) || (this
						.getWeFunction() != null
						&& castOther.getWeFunction() != null && this
						.getWeFunction().equals(castOther.getWeFunction())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getWeRole() == null ? 0 : this.getWeRole().hashCode());
		result = 37
				* result
				+ (getWeFunction() == null ? 0 : this.getWeFunction()
						.hashCode());
		return result;
	}

}