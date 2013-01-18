package com.thinkgem.webeffect.interceptor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.thinkgem.webeffect.action.BaseAction;
import com.thinkgem.webeffect.model.WeUser;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 访问权限拦截器
 * @author WangZhen <thinkgem@163.com>
 * @created 2010-4-19
 * @version $Id: EvaAuthInterceptor.java 2500 2010-05-21 20:35:46Z wzhen $
 */
@SuppressWarnings("unchecked")
public class AuthInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 632293647099709993L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map session = invocation.getInvocationContext().getSession();
		if (session.containsKey("user")){
			WeUser u = (WeUser)session.get("user");
			if (u.getRole().intValue() == 2){
				Set<String> set = new HashSet<String>();
				//普通 用户拥有的权限
				set.add("/effect/index");//主页
				set.add("/effect/upload");//上传特效
				set.add("/effect/uploadfile");//上传特效文件
				set.add("/effect/edit");//编辑特效
				set.add("/effect/delete");//删除特效
				set.add("/effect/view");//查看特效
				set.add("/effect/download");//下载特效
				set.add("/effect/comment");//浏览评论
				set.add("/effect/addComment");//发表评论
				set.add("/effect/getNickname");//获得昵称
				set.add("/center/changeInfo");//修改个人信息
				set.add("/center/changePassword");//修改个人密码
				//获得当前URI
				HttpServletRequest request = ServletActionContext.getRequest();
				String uri = request.getRequestURI();
				uri = uri.substring(0,uri.lastIndexOf(".")).replace(request.getContextPath(), "");			
//				System.out.println(uri);
				//判断是否拥有权限
				if(!set.contains(uri)){
					return "403";
				}
			}
			return invocation.invoke();
		}else{
//			if (invocation.getAction() instanceof com.opensymphony.xwork2.ActionSupport) {
//				String msg = "你当前还没有登录！<a href=\""+ServletActionContext.getRequest().getContextPath()+"/login.do\">点击这里登录</a>";
//				((com.opensymphony.xwork2.ActionSupport)invocation.getAction()).addActionMessage(msg);
//			}
//			return BaseAction.MESSAGE;
			return BaseAction.LOGIN;
		}
	}
}