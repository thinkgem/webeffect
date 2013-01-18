package com.thinkgem.webeffect.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.thinkgem.webeffect.model.WeUser;
import com.thinkgem.webeffect.service.UserService;

/**
 * 默认控制器
 * @author WangZhen <thinkgem@163.com>
 * @version $Id$
 */
@Scope("prototype")
@Controller("defaultAction")
public class DefaultAction extends ValidateAction {

	private static final long serialVersionUID = -4859608488707100341L;
	
//	private String uri;
	
	private String username;
	private String password;
	
	private WeUser user;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 引导
	 * @return
	 */
	public String index(){
		return this.getCurrentUser() == null ? LOGIN : SUCCESS;
	}
	
	/**
	 * 登录
	 * @return
	 */
	public String login(){
		if(isPost()){
			user = userService.login(username, password);
			if (user==null){
				this.addActionError("用户名或密码不正确");
			}else{
				this.getRequest().getSession().setAttribute("user", user);
				return SUCCESS;
			}
		}
		return INPUT;
	}
	public void validateLogin(){
		if (isPost()){
			requiredStringValidate("用户名", username);
			requiredStringValidate("密码", password);
		}
	}
	
	/**
	 * 登出
	 * @return
	 */
	public String logout(){
		this.getRequest().getSession().removeAttribute("user");
		return LOGIN;
	}
	
//	/**
//	 * 权限验证
//	 * @param uri 需验证的URI
//	 * @return 通过：true
//	 */
//	public String auth(){
//		WeUser u = getCurrentUser();
//		if (u != null){
//			if (u.getRole().intValue() == 2){
//				Set<String> set = new HashSet<String>();
//				//普通 用户拥有的权限
//				set.add("/effect/index");
//				set.add("/effect/upload");
//				set.add("/effect/uploadfile");
//				set.add("/effect/view");
//				set.add("/effect/download");
//				//判断是否拥有权限
//				if(!set.contains(uri)){
//					return renderText("false");
//				}
//			}
//			return renderText("true");
//		}
//		return renderText("false");
//	}
	
	//---------- get set

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
