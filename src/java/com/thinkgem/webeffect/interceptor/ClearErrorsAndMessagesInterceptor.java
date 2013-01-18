package com.thinkgem.webeffect.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 清除错误及消息拦截器
 * @author WangZhen <thinkgem@163.com>
 * @created 2010-3-31
 * @version $Id: ClearErrorsAndMessagesInterceptor.java 2500 2010-05-21 20:35:46Z wzhen $
 */
public class ClearErrorsAndMessagesInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -6809721511919203053L;

	public String intercept(ActionInvocation invocation) throws Exception {
		((ActionSupport)invocation.getAction()).clearErrorsAndMessages();
		return invocation.invoke();
	}
}