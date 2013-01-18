package com.thinkgem.webeffect.model;

import java.sql.Timestamp;

import com.thinkgem.webeffect.util.Utils;

/**
 * WeUser entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings({"serial" })
public class WeUser implements java.io.Serializable {

	// Fields

	private Integer id;
	private String username;
	private String password;
	private String passwordNew;
	private String passwordNew2;
	private String nickname;
	private String description;
	private Integer role;
	private Integer loginNum;
	private Timestamp loginTime;

	// Constructors

	/** default constructor */
	public WeUser() {
	}

	/** minimal constructor */
	public WeUser(String username, String password, String nickname,
			Integer role, Integer loginNum, Timestamp loginTime) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.role = role;
		this.loginNum = loginNum;
		this.loginTime = loginTime;
	}

	/** full constructor */
	public WeUser(String username, String password, String nickname,
			String description, Integer role, Integer loginNum,
			Timestamp loginTime) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.description = description;
		this.role = role;
		this.loginNum = loginNum;
		this.loginTime = loginTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getRole() {
		return this.role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getLoginNum() {
		return this.loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}

	public Timestamp getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}
	
	// formatted
	
	public String getRoleFormatted(){
		String str = "";
		switch(this.role.intValue()){
		case 1:
			str = "系统管理员";
			break;
		case 2:
			str = "普通用户";
			break;
//		case 3:
//			str = "普通用户";
		default:
			str = "未知";
		}
		return str;
	}
	
	public String getLoginTimeFormatted(){
		return Utils.dataFormat(this.getLoginTime(), "yyyy-MM-dd");
	}

	public String getPasswordNew() {
		return passwordNew;
	}

	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}

	public String getPasswordNew2() {
		return passwordNew2;
	}

	public void setPasswordNew2(String passwordNew2) {
		this.passwordNew2 = passwordNew2;
	}


}