package com.thinkgem.webeffect.service;

import java.util.List;

import com.thinkgem.webeffect.model.WeCategory;

/**
 * 特效分类服务接口
 * @author WangZhen <thinkgem@163.com>
 * @version $Id$
 */
public interface CategoryService {
	
	/**
	 * 获得分类对象
	 * @param id
	 * @return
	 */
	WeCategory get(int id);
	
	/**
	 * 保存或更新分类对象
	 * @param effect
	 */
	void saveOrUpdate(WeCategory category);
	
	/**
	 * 保存或更新全部分类对象
	 * @param effect
	 */
	void saveOrUpdateAll(List<WeCategory> categoryList);
	
	/**
	 * 通过分类编号删除
	 * @param id 分类编号
	 */
	void delete(int id);

	/**
	 * 获得特效列表，排序：权重降序
	 * @return
	 */
	List<WeCategory> list();
	
}
