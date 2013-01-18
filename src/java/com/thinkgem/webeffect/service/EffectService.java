package com.thinkgem.webeffect.service;

import java.util.List;

import com.thinkgem.webeffect.hibernate.Pagination;
import com.thinkgem.webeffect.model.WeComment;
import com.thinkgem.webeffect.model.WeEffect;
import com.thinkgem.webeffect.model.WeFile;

/**
 * 特效服务接口
 * @author WangZhen <thinkgem@163.com>
 * @version $Id$
 */
public interface EffectService {
	
	/**
	 * 获得特效对象
	 * @param id
	 * @return
	 */
	WeEffect get(int id);
	
	/**
	 * 保存或更新特效对象
	 * @param effect
	 */
	void saveOrUpdate(WeEffect effect);

	/**
	 * 获得特效列表
	 * @param categoryId 分类编号，若为0则查询全部分类
	 * @param pageIndex 当前页
	 * @param pageSize 页面大小
	 * @param query 查询字符串
	 * @return 页码对象
	 */
	Pagination list(int categoryId, int pageIndex, int pageSize, String query);
	
	/**
	 * 获得特效最热门点击的特效
	 * @param categoryId 获得的分类编号，若为0则查询全部分类
	 * @param top 获得数量
	 * @return 特效列表
	 */
	List<WeEffect> topClickList(int categoryId, int top);
	
	/**
	 * 获得特效最热门下载的特效
	 * @param categoryId 获得的分类编号，若为0则查询全部分类
	 * @param top 获得数量
	 * @return 特效列表
	 */
	List<WeEffect> topDownList(int categoryId, int top);
	
	
	/**
	 * 获得特效文件
	 * @param id
	 * @return
	 */
	WeFile getFile(int id);
	
	/**
	 * 保存特效文件
	 * @param file
	 */
	void saveFile(WeFile file);
	
	/**
	 * 删除特效信息及特效文件
	 * @param id 特效编号
	 */
	boolean delete(int id);
	
	/**
	 * 检查用户名是否存在
	 * @param name 文件名 HASH 值
	 * @return true: 存在
	 */
	boolean fileExists(String name);
	
	/**
	 * 单击次数加1
	 * @param id 特效编号
	 */
	void clickNumAdd(int id);
	
	/**
	 * 下载次数加1
	 * @param id 特效编号
	 */
	void downNumAdd(int fileId);
	
	/**
	 * 评论列表
	 * @param effectId 特效编号
	 * @param pageIndex 当前页
	 * @param pageSize 页面大小
	 * @return 评论列表页面对象
	 */
	Pagination commentList(int effectId, int pageIndex, int pageSize);
	
	/**
	 * 保存评论列表
	 * @param comment
	 */
	void saveComment(WeComment comment);
	
	/**
	 * 删除评论内容
	 * @param commentId
	 */
	void deleteComment(int commentId);
	
	/**
	 * 获得昵称
	 * @param username 用户名 
	 * @return
	 */
	String getNickName(String username);
}
