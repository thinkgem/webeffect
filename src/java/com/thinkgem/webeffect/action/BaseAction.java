package com.thinkgem.webeffect.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkgem.webeffect.hibernate.Pagination;
import com.thinkgem.webeffect.model.WeUser;
import com.opensymphony.xwork2.ActionContext;

/**
 * Action 基类
 * @author WangZhen <thinkgem@163.com>
 * @version $Id: BaseAction.java 2500 2010-05-21 20:35:46Z wzhen $
 */
public class BaseAction extends com.opensymphony.xwork2.ActionSupport {

	private static final long serialVersionUID = 6879997137880435518L;
	protected static final Logger log = LoggerFactory.getLogger(BaseAction.class);
	
	public int pageIndex = 1;
	protected int pageSize = 30;
	protected Pagination pagination;
	
	public static final String MESSAGE = "message";
	
	public int getPageIndex() {
		return pageIndex;
	}
	
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	
	/**
	 * 获取主题资源路径
	 */
	public String getResPath(){
		return this.getRequest().getContextPath()+getTexts("struts").getString("status.resPath");
	}
	
	/**
	 * 获取主题模板路径
	 */
	public String getTplPath(){
		return getTexts("struts").getString("status.tplPath");
	}

	/**
	 * 获得请求对象
	 */
	protected HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}
	
	/**
	 * 获得客户端响应对象
	 */
	protected HttpServletResponse getResponse(){
		return ServletActionContext.getResponse();
	}
	
	/**
	 * 获得客户端会话对象
	 */
	protected HttpSession getSession(){
		return this.getRequest().getSession();
	}

	/**
	 * 获得服务器session对象
	 */
	@SuppressWarnings("unchecked")
	protected Map getSessionMap(){		
		return ActionContext.getContext().getSession();
	}
	
	/**
	 * 获得HTTP的请求方法
	 */
	protected String getMethod(){
		return this.getRequest().getMethod();
	}
	
	/**
	 * HTTP是否以Get方式请求
	 */
	protected boolean isGet(){
		return "GET".equals(this.getMethod());
	}
	
	/**
	 * HTTP是否以POST方式请求
	 */	
	protected boolean isPost(){
		return "POST".equals(this.getMethod());
	}

	/**
	 * 获得GET或POST参数
	 */
	protected String getParameter(String s){
		return this.getRequest().getParameter(s);
	}
	
	/**
	 * 直接输出Text
	 */
	protected String renderText(String text) {
		return render(text, "text/plain;charset=utf-8");
	}
	
	/**
	 * 直接输出HTML
	 */
	protected String renderHtml(String html) {
		return render(html, "text/html;charset=utf-8");
	}

	/**
	 * 直接输出XML
	 */
	protected String renderXML(String xml) {
		return render(xml, "text/xml;charset=utf-8");
	}
	
	/**
	 * 直接输出JavaScript
	 */
	protected String renderJavaScript(String html) {
		return render(html, "text/javascript;charset=utf-8");
	}
	
	/**
	 * 直接输出内容
	 */
	protected String render(String s, String contentType) {
		try {
			HttpServletResponse response = this.getResponse();
			response.setContentType(contentType);
			response.getWriter().write(s);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return NONE;
	}
	
	protected WeUser getCurrentUser(){
		Object user = ServletActionContext.getRequest().getSession().getAttribute("user");
		return user == null ? null : (WeUser)user;
	}
	
}
