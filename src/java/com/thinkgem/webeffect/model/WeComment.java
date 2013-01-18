package com.thinkgem.webeffect.model;

import java.sql.Timestamp;

/**
 * WeComment entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class WeComment implements java.io.Serializable {

	// Fields

	private Integer id;
	private WeEffect weEffect;
	private String username;
	private String content;
	private Timestamp created;

	// Constructors

	/** default constructor */
	public WeComment() {
	}

	/** full constructor */
	public WeComment(WeEffect weEffect, String username, String content,
			Timestamp created) {
		this.weEffect = weEffect;
		this.username = username;
		this.content = content;
		this.created = created;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WeEffect getWeEffect() {
		return this.weEffect;
	}

	public void setWeEffect(WeEffect weEffect) {
		this.weEffect = weEffect;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

}