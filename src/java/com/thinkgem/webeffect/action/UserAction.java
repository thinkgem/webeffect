package com.thinkgem.webeffect.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.thinkgem.webeffect.model.WeUser;
import com.thinkgem.webeffect.service.UserService;
import com.thinkgem.webeffect.util.Encrypt;

/**
 * 用户控制器
 * @author WangZhen <thinkgem@163.com>
 * @version $Id$
 */
@Scope("prototype")
@Controller("userAction")
public class UserAction extends ValidateAction {
	
	private static final long serialVersionUID = 5087739323925878936L;
	
	private int id;
	
	private Map<Integer, String> roleMap;
	
	private WeUser user;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 用户管理首页
	 * @return
	 */
	public String index(){
		pagination = userService.list(pageIndex, pageSize);
		return SUCCESS;
	}
	
	/**
	 * 新增用户
	 * @return
	 */
	public String add(){
		if(isPost()){
			if(userService.usernameExists(user.getUsername())){
				this.addActionError("该用户名已经存在");
				return INPUT;
			}
			userService.save(user);
			return renderText(SUCCESS);
		}
		return SUCCESS;
	}
	public void validateAdd(){
		if (isPost() && user!=null){
			this.usernameValidate(user.getUsername());
			this.rangeLengthValidate("密码", user.getPassword(), 4, 20);
			this.rangeLengthValidate("昵称", user.getNickname(), 2, 20);
			this.maxLengthValidate("描述", user.getDescription(), 255);
			if(!this.requiredValidate("角色", user.getRole())){
				this.rangeValidate("角色", user.getRole(), 1, 2);
			}
		}
	}
	
	/**
	 * 编辑用户
	 * @return
	 */
	public String edit(){
		if(isPost()){
			WeUser u = userService.getById(user.getId());
			u.setNickname(user.getNickname());
			u.setDescription(user.getDescription());
			u.setRole(user.getRole());
			if (user.getPassword()!=null && !"".equals(user.getPassword())) 
				u.setPassword(Encrypt.md5(Encrypt.sha(user.getPassword())));
			userService.update(u);
			return renderText(SUCCESS);
		}else{
			user = userService.getById(id);			
		}
		return SUCCESS;
	}
	public void validateEdit(){
		if (isPost() && user!=null){
			if (user.getPassword()!=null && !"".equals(user.getPassword())) 
				this.rangeLengthValidate("密码", user.getPassword(), 4, 20);
			this.rangeLengthValidate("昵称", user.getNickname(), 2, 20);
			this.maxLengthValidate("描述", user.getDescription(), 255);
			if(!this.requiredValidate("角色", user.getRole())){
				this.rangeValidate("角色", user.getRole(), 1, 2);
			}
		}
	}
	
	/**
	 * 删除用户
	 * @return
	 */
	public String delete(){
		if (id == 1 || getCurrentUser().getId().intValue() == id){//不能删除自己及最高管理员
			return renderText("不能删除该用户");
		}
		userService.delete(id);
		return renderText(SUCCESS);
	}
	
	/**
	 * 修改个人资料
	 * @return
	 */
	public String changeInfo(){
		if(isPost()){
			WeUser u = userService.getById(getCurrentUser().getId());
			u.setNickname(user.getNickname());
			u.setDescription(user.getDescription());
			userService.update(u);
			return renderText(SUCCESS);
		}else{
			user = userService.getById(getCurrentUser().getId());
		}
		return SUCCESS;
	}
	public void validateChangeInfo(){
		if (isPost() && user!=null){
			this.rangeLengthValidate("昵称", user.getNickname(), 2, 20);
			this.maxLengthValidate("描述", user.getDescription(), 255);
		}
	}
	/**
	 * 修改密码
	 * @return
	 */
	public String changePassword(){
		if(isPost()){
			WeUser u = userService.getById(getCurrentUser().getId());			
			if(!u.getPassword().equals(Encrypt.md5(Encrypt.sha(user.getPassword())))){
				this.addActionError("旧密码不正确");
				return INPUT;
			}
			u.setPassword(Encrypt.md5(Encrypt.sha(user.getPasswordNew())));
			userService.update(u);
			return renderText(SUCCESS);
		}else{
			user = userService.getById(getCurrentUser().getId());
		}
		return SUCCESS;
	}
	public void validateChangePassword(){
		if (isPost() && user!=null){
			this.rangeLengthValidate("新密码", user.getPasswordNew(), 4, 20);
			this.equalStringValidate("新密码与确认新密码", user.getPasswordNew(), user.getPasswordNew2());
		}
	}

	public Map<Integer, String> getRoleMap() {
		roleMap = new HashMap<Integer, String>();
		roleMap.put(1, "系统管理员");
		roleMap.put(2, "普通用户");
//		roleMap.put(3, "普通用户");
		return roleMap;
	}

	public void setRoleMap(Map<Integer, String> roleMap) {
		this.roleMap = roleMap;
	}

	public WeUser getUser() {
		return user;
	}

	public void setUser(WeUser user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}
